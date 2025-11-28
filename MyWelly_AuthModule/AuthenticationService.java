import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class AuthenticationService {

    public Long registerUser(String username, String email, String password,
                            String phoneNumber, UserType userType) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            // Check if username or email already exists
            String checkSql = "SELECT user_id FROM users WHERE user_name = ? OR email = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                throw new SQLException("Username or email already exists");
            }
            rs.close();
            stmt.close();

            // Hash the password
            String hashedPassword = PasswordUtil.hashPassword(password);

            // Insert new user
            String insertSql = "INSERT INTO users (user_name, email, password_hash, phone_number, user_type, is_verified, is_active, account_locked, failed_login_attempts) VALUES (?, ?, ?, ?, ?, false, true, false, 0)";
            stmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, userType.name());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected");
            }

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    public UserEntity login(String usernameOrEmail, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            // Query user by username or email
            String sql = "SELECT user_id, user_name, email, phone_number, user_type, password_hash, is_verified, is_active, account_locked, failed_login_attempts FROM users WHERE user_name = ? OR email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Invalid username/email or password");
            }

            Long userId = rs.getLong("user_id");
            String userName = rs.getString("user_name");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phone_number");
            String userTypeStr = rs.getString("user_type");
            String passwordHash = rs.getString("password_hash");
            boolean isVerified = rs.getBoolean("is_verified");
            boolean isActive = rs.getBoolean("is_active");
            boolean accountLocked = rs.getBoolean("account_locked");
            int failedAttempts = rs.getInt("failed_login_attempts");

            // Check if account is locked
            if (accountLocked) {
                throw new SQLException("Account is locked. Please contact support.");
            }

            // Check if account is active
            if (!isActive) {
                throw new SQLException("Account is inactive. Please contact support.");
            }

            // Verify password
            if (!PasswordUtil.verifyPassword(password, passwordHash)) {
                // Increment failed login attempts
                failedAttempts++;
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET failed_login_attempts = ?, account_locked = ? WHERE user_id = ?");
                updateStmt.setInt(1, failedAttempts);
                updateStmt.setBoolean(2, failedAttempts >= 5);
                updateStmt.setLong(3, userId);
                updateStmt.executeUpdate();
                updateStmt.close();

                if (failedAttempts >= 5) {
                    throw new SQLException("Account locked due to too many failed login attempts");
                }
                throw new SQLException("Invalid username/email or password");
            }

            // Reset failed login attempts on successful login
            if (failedAttempts > 0) {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET failed_login_attempts = 0 WHERE user_id = ?");
                updateStmt.setLong(1, userId);
                updateStmt.executeUpdate();
                updateStmt.close();
            }

            UserType userType = UserType.valueOf(userTypeStr);
            return new UserEntity(userId, userName, email, phoneNumber, userType, isVerified, isActive, accountLocked, 0);

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    public boolean verifyEmail(Long userId, String verificationCode) throws SQLException {
        // TODO: Implement email verification logic
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public void requestPasswordReset(String email) throws SQLException {
        // TODO: Implement password reset request logic
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public boolean resetPassword(String email, String verificationCode,
                                String newPassword) throws SQLException {
        // TODO: Implement password reset logic
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public boolean changePassword(Long userId, String currentPassword,
                                 String newPassword) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            // Get current password hash
            String sql = "SELECT password_hash FROM users WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new SQLException("User not found");
            }

            String passwordHash = rs.getString("password_hash");

            // Verify current password
            if (!PasswordUtil.verifyPassword(currentPassword, passwordHash)) {
                throw new SQLException("Current password is incorrect");
            }

            rs.close();
            stmt.close();

            // Update to new password
            String newPasswordHash = PasswordUtil.hashPassword(newPassword);
            String updateSql = "UPDATE users SET password_hash = ? WHERE user_id = ?";
            stmt = conn.prepareStatement(updateSql);
            stmt.setString(1, newPasswordHash);
            stmt.setLong(2, userId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
