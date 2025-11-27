import java.sql.Timestamp;

public class VerificationCode {
    private int codeId;
    private int userId;
    private String code;
    private VerificationPurpose purpose;
    private Timestamp createdAt;
    private Timestamp expiresAt;
    private boolean used;

    public VerificationCode() {}

    public int getCodeId() { return codeId; }
    public void setCodeId(int codeId) { this.codeId = codeId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public VerificationPurpose getPurpose() { return purpose; }
    public void setPurpose(VerificationPurpose purpose) { this.purpose = purpose; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Timestamp expiresAt) { this.expiresAt = expiresAt; }

    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }
}
