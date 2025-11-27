import java.sql.Date;
import java.sql.Timestamp;

/**
 * Patient Entity
 */
public class Patient {
    private int patientId;
    private int userId;
    private String fullName;
    private Date dateOfBirth;
    private String gender; // "Male" or "Female" only
    private String idNumber;
    private String phoneNumber;
    private String personalEmail;
    private int noShowCount;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Patient() {}

    public Patient(int patientId, int userId, String fullName, Date dateOfBirth,
                  String gender, String idNumber, String phoneNumber, String personalEmail) {
        this.patientId = patientId;
        this.userId = userId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.personalEmail = personalEmail;
        this.noShowCount = 0;
    }

    // Getters and Setters
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) {
        // Validate gender - only Male or Female allowed
        if ("Male".equals(gender) || "Female".equals(gender)) {
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("Gender must be either 'Male' or 'Female'");
        }
    }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPersonalEmail() { return personalEmail; }
    public void setPersonalEmail(String personalEmail) { this.personalEmail = personalEmail; }

    public int getNoShowCount() { return noShowCount; }
    public void setNoShowCount(int noShowCount) { this.noShowCount = noShowCount; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
