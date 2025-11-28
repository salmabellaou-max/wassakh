// 
// Decompiled by Procyon v0.6.0
// 

class UserEntity
{
    private Long userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private boolean isVerified;
    private boolean isActive;
    private boolean accountLocked;
    private int failedLoginAttempts;
    
    public UserEntity(final Long userId, final String userName, final String email, final String phoneNumber, final UserType userType, final boolean isVerified, final boolean isActive, final boolean accountLocked, final int failedLoginAttempts) {
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
    
    public Long getUserId() {
        return this.userId;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public UserType getUserType() {
        return this.userType;
    }
    
    public boolean isVerified() {
        return this.isVerified;
    }
    
    public boolean isActive() {
        return this.isActive;
    }
    
    public boolean isAccountLocked() {
        return this.accountLocked;
    }
    
    public int getFailedLoginAttempts() {
        return this.failedLoginAttempts;
    }
    
    public void setVerified(final boolean verified) {
        this.isVerified = verified;
    }
    
    public void setActive(final boolean active) {
        this.isActive = active;
    }
    
    public void setAccountLocked(final boolean locked) {
        this.accountLocked = locked;
    }
    
    public void setFailedLoginAttempts(final int attempts) {
        this.failedLoginAttempts = attempts;
    }
}
