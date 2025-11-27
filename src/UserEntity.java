import java.sql.Timestamp;

/**
 * User Entity - Base user account information
 */
public class UserEntity {
    private int userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private boolean isVerified;
    private boolean isActive;
    private boolean accountLocked;
    private int failedLoginAttempts;
    private Timestamp createdAt;
    private Timestamp lastLogin;

    // Constructors
    public UserEntity() {}

    public UserEntity(int userId, String userName, String email, String phoneNumber,
                     UserType userType, boolean isVerified, boolean isActive,
                     boolean accountLocked, int failedLoginAttempts) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.isVerified = isVerified;
        this.isActive = isActive;
        this.accountLocked = accountLocked;
        this.failedLoginAttempts = failedLoginAttempts;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public boolean isAccountLocked() { return accountLocked; }
    public void setAccountLocked(boolean accountLocked) { this.accountLocked = accountLocked; }

    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(int failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getLastLogin() { return lastLogin; }
    public void setLastLogin(Timestamp lastLogin) { this.lastLogin = lastLogin; }
}
