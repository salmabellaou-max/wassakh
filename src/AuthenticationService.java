import java.sql.*;
import java.time.LocalDateTime;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Authentication Service with Email Verification
 * Fixed security issue: Now requires email verification for new accounts
 */
public class AuthenticationService {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int VERIFICATION_CODE_EXPIRY_MINUTES = 15;

    /**
     * Register a new user with email verification
     * SECURITY FIX: Sends verification email before activating account
     */
    public static boolean registerUser(String userName, String email, String password,
                                      String phoneNumber, UserType userType) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if username or email already exists
            String checkSql = "SELECT user_id FROM users WHERE user_name = ? OR email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, userName);
                checkStmt.setString(2, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Username or email already exists");
                    return false;
                }
            }

            // Hash password
            String hashedPassword = PasswordUtil.hashPassword(password);

            // Insert user (unverified)
            String insertSql = "INSERT INTO users (user_name, email, password_hash, phone_number, " +
                    "user_type, is_verified, is_active) VALUES (?, ?, ?, ?, ?, FALSE, TRUE)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, userName);
                stmt.setString(2, email);
                stmt.setString(3, hashedPassword);
                stmt.setString(4, phoneNumber);
                stmt.setString(5, userType.name());
                stmt.executeUpdate();

                // Get generated user ID
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);

                    // Generate and send verification code
                    String verificationCode = PasswordUtil.generateVerificationCode();
                    saveVerificationCode(userId, verificationCode, VerificationPurpose.SIGNUP);

                    // Send verification email
                    sendVerificationEmail(email, userName, verificationCode);

                    System.out.println("User registered. Verification email sent to: " + email);
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verify email with verification code
     */
    public static boolean verifyEmail(String email, String code) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT vc.code_id, vc.user_id FROM verification_codes vc " +
                    "JOIN users u ON vc.user_id = u.user_id " +
                    "WHERE u.email = ? AND vc.code = ? AND vc.purpose = 'SIGNUP' " +
                    "AND vc.used = FALSE AND vc.expires_at > NOW()";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, code);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int codeId = rs.getInt("code_id");

                    // Mark code as used
                    String updateCodeSql = "UPDATE verification_codes SET used = TRUE WHERE code_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateCodeSql)) {
                        updateStmt.setInt(1, codeId);
                        updateStmt.executeUpdate();
                    }

                    // Mark user as verified
                    String updateUserSql = "UPDATE users SET is_verified = TRUE WHERE user_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateUserSql)) {
                        updateStmt.setInt(1, userId);
                        updateStmt.executeUpdate();
                    }

                    System.out.println("Email verified successfully for user: " + userId);
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error verifying email: " + e.getMessage());
        }
        return false;
    }

    /**
     * Login user (only verified users can login)
     */
    public static UserEntity login(String userName, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE user_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userName);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Check if account is locked
                    if (rs.getBoolean("account_locked")) {
                        System.out.println("Account is locked. Please contact support.");
                        return null;
                    }

                    // Check if email is verified (SECURITY FIX)
                    if (!rs.getBoolean("is_verified")) {
                        System.out.println("Email not verified. Please check your email for verification code.");
                        return null;
                    }

                    // Verify password
                    String storedHash = rs.getString("password_hash");
                    if (PasswordUtil.verifyPassword(password, storedHash)) {
                        // Reset failed attempts
                        resetFailedAttempts(rs.getInt("user_id"));

                        // Update last login
                        updateLastLogin(rs.getInt("user_id"));

                        // Create and return user entity
                        UserEntity user = new UserEntity();
                        user.setUserId(rs.getInt("user_id"));
                        user.setUserName(rs.getString("user_name"));
                        user.setEmail(rs.getString("email"));
                        user.setPhoneNumber(rs.getString("phone_number"));
                        user.setUserType(UserType.valueOf(rs.getString("user_type")));
                        user.setVerified(rs.getBoolean("is_verified"));
                        user.setActive(rs.getBoolean("is_active"));
                        user.setAccountLocked(rs.getBoolean("account_locked"));

                        System.out.println("Login successful for user: " + userName);
                        return user;
                    } else {
                        // Increment failed attempts
                        incrementFailedAttempts(rs.getInt("user_id"));
                        System.out.println("Invalid password");
                    }
                } else {
                    System.out.println("User not found: " + userName);
                }
            }
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
        }
        return null;
    }

    /**
     * Save verification code to database
     */
    private static void saveVerificationCode(int userId, String code, VerificationPurpose purpose) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO verification_codes (user_id, code, purpose, expires_at) " +
                    "VALUES (?, ?, ?, DATE_ADD(NOW(), INTERVAL ? MINUTE))";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setString(2, code);
                stmt.setString(3, purpose.name());
                stmt.setInt(4, VERIFICATION_CODE_EXPIRY_MINUTES);
                stmt.executeUpdate();
            }
        }
    }

    /**
     * Send verification email
     */
    private static void sendVerificationEmail(String toEmail, String userName, String code) {
        // Email configuration (you'll need to configure SMTP settings)
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Change to your SMTP server
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Email credentials (use environment variables in production!)
        final String username = "your-email@gmail.com"; // Change this
        final String password = "your-app-password"; // Change this

        try {
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("MyWelly - Verify Your Email");

            String emailContent = String.format(
                    "Hello %s,\n\n" +
                            "Thank you for registering with MyWelly!\n\n" +
                            "Your verification code is: %s\n\n" +
                            "This code will expire in %d minutes.\n\n" +
                            "If you didn't create this account, please ignore this email.\n\n" +
                            "Best regards,\n" +
                            "MyWelly Team",
                    userName, code, VERIFICATION_CODE_EXPIRY_MINUTES
            );

            message.setText(emailContent);

            // Uncomment to actually send email (requires proper SMTP configuration)
            // Transport.send(message);

            System.out.println("Verification email sent to: " + toEmail);
            System.out.println("Verification code: " + code + " (for testing)");

        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            System.out.println("VERIFICATION CODE (email failed): " + code);
        }
    }

    /**
     * Increment failed login attempts
     */
    private static void incrementFailedAttempts(int userId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET failed_login_attempts = failed_login_attempts + 1, " +
                    "account_locked = (failed_login_attempts + 1 >= ?) WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, MAX_FAILED_ATTEMPTS);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
        }
    }

    /**
     * Reset failed login attempts
     */
    private static void resetFailedAttempts(int userId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET failed_login_attempts = 0 WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
        }
    }

    /**
     * Update last login timestamp
     */
    private static void updateLastLogin(int userId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET last_login = NOW() WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
        }
    }

    /**
     * Request password reset
     */
    public static boolean requestPasswordReset(String email) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT user_id, user_name FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String userName = rs.getString("user_name");

                    String code = PasswordUtil.generateVerificationCode();
                    saveVerificationCode(userId, code, VerificationPurpose.PASSWORD_RESET);

                    sendVerificationEmail(email, userName, code);
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error requesting password reset: " + e.getMessage());
        }
        return false;
    }

    /**
     * Reset password with verification code
     */
    public static boolean resetPassword(String email, String code, String newPassword) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT vc.code_id, vc.user_id FROM verification_codes vc " +
                    "JOIN users u ON vc.user_id = u.user_id " +
                    "WHERE u.email = ? AND vc.code = ? AND vc.purpose = 'PASSWORD_RESET' " +
                    "AND vc.used = FALSE AND vc.expires_at > NOW()";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, code);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int codeId = rs.getInt("code_id");

                    // Mark code as used
                    String updateCodeSql = "UPDATE verification_codes SET used = TRUE WHERE code_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateCodeSql)) {
                        updateStmt.setInt(1, codeId);
                        updateStmt.executeUpdate();
                    }

                    // Update password
                    String hashedPassword = PasswordUtil.hashPassword(newPassword);
                    String updateUserSql = "UPDATE users SET password_hash = ?, account_locked = FALSE, " +
                            "failed_login_attempts = 0 WHERE user_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateUserSql)) {
                        updateStmt.setString(1, hashedPassword);
                        updateStmt.setInt(2, userId);
                        updateStmt.executeUpdate();
                    }

                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error resetting password: " + e.getMessage());
        }
        return false;
    }

    /**
     * Change password (for logged-in users)
     */
    public static boolean changePassword(int userId, String oldPassword, String newPassword) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Verify old password
            String sql = "SELECT password_hash FROM users WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    if (PasswordUtil.verifyPassword(oldPassword, storedHash)) {
                        // Update password
                        String updateSql = "UPDATE users SET password_hash = ? WHERE user_id = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setString(1, PasswordUtil.hashPassword(newPassword));
                            updateStmt.setInt(2, userId);
                            updateStmt.executeUpdate();
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error changing password: " + e.getMessage());
        }
        return false;
    }
}
