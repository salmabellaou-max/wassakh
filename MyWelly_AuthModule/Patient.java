import java.time.LocalDate;

// 
// Decompiled by Procyon v0.6.0
// 

class Patient
{
    private Long patientId;
    private Long userId;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String idNumber;
    private String phoneNumber;
    private String personalEmail;
    private Integer noShowCount;
    
    public Patient(final Long patientId, final Long userId, final String fullName, final LocalDate dateOfBirth, final String gender, final String idNumber, final String phoneNumber, final String personalEmail, final Integer noShowCount) {
        this.patientId = patientId;
        this.userId = userId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.personalEmail = personalEmail;
        this.noShowCount = noShowCount;
    }
    
    public Long getPatientId() {
        return this.patientId;
    }
    
    public Long getUserId() {
        return this.userId;
    }
    
    public String getFullName() {
        return this.fullName;
    }
    
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }
    
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }
    
    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getGender() {
        return this.gender;
    }
    
    public void setGender(final String gender) {
        this.gender = gender;
    }
    
    public String getIdNumber() {
        return this.idNumber;
    }
    
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getPersonalEmail() {
        return this.personalEmail;
    }
    
    public void setPersonalEmail(final String personalEmail) {
        this.personalEmail = personalEmail;
    }
    
    public Integer getNoShowCount() {
        return this.noShowCount;
    }
}
