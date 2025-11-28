import java.time.chrono.ChronoLocalDateTime;
import java.time.LocalDateTime;

// 
// Decompiled by Procyon v0.6.0
// 

class VerificationCode
{
    private Long verificationId;
    private Long userId;
    private String code;
    private VerificationPurpose purpose;
    private boolean isUsed;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    
    public VerificationCode(final Long verificationId, final Long userId, final String code, final VerificationPurpose purpose, final boolean isUsed, final LocalDateTime expiresAt, final LocalDateTime createdAt) {
        this.verificationId = verificationId;
        this.userId = userId;
        this.code = code;
        this.purpose = purpose;
        this.isUsed = isUsed;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
    }
    
    public boolean isValid() {
        return !this.isUsed && LocalDateTime.now().isBefore(this.expiresAt);
    }
    
    public Long getVerificationId() {
        return this.verificationId;
    }
    
    public Long getUserId() {
        return this.userId;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public VerificationPurpose getPurpose() {
        return this.purpose;
    }
    
    public boolean isUsed() {
        return this.isUsed;
    }
    
    public LocalDateTime getExpiresAt() {
        return this.expiresAt;
    }
}
