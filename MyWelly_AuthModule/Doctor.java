import java.util.ArrayList;
import java.util.List;

// 
// Decompiled by Procyon v0.6.0
// 

class Doctor
{
    private Long doctorId;
    private Long userId;
    private String fullName;
    private String specialty;
    private String clinicName;
    private String address;
    private String city;
    private String phoneNumber;
    private Double consultationFees;
    private Integer yearsOfExperience;
    private String bio;
    private Double rating;
    private Integer reviewCount;
    private List<Certificate> certificates;
    private boolean isAcceptingPatients;
    
    public Doctor(final Long doctorId, final Long userId, final String fullName, final String specialty, final String clinicName, final String address, final String city, final String phoneNumber, final Double consultationFees, final Integer yearsOfExperience, final String bio, final Double rating, final Integer reviewCount, final boolean isAcceptingPatients) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.fullName = fullName;
        this.specialty = specialty;
        this.clinicName = clinicName;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.consultationFees = consultationFees;
        this.yearsOfExperience = yearsOfExperience;
        this.bio = bio;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.certificates = new ArrayList<Certificate>();
        this.isAcceptingPatients = isAcceptingPatients;
    }
    
    public Long getDoctorId() {
        return this.doctorId;
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
    
    public String getSpecialty() {
        return this.specialty;
    }
    
    public void setSpecialty(final String specialty) {
        this.specialty = specialty;
    }
    
    public String getClinicName() {
        return this.clinicName;
    }
    
    public void setClinicName(final String clinicName) {
        this.clinicName = clinicName;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(final String city) {
        this.city = city;
    }
    
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Double getConsultationFees() {
        return this.consultationFees;
    }
    
    public void setConsultationFees(final Double consultationFees) {
        this.consultationFees = consultationFees;
    }
    
    public Integer getYearsOfExperience() {
        return this.yearsOfExperience;
    }
    
    public void setYearsOfExperience(final Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    public String getBio() {
        return this.bio;
    }
    
    public void setBio(final String bio) {
        this.bio = bio;
    }
    
    public Double getRating() {
        return this.rating;
    }
    
    public Integer getReviewCount() {
        return this.reviewCount;
    }
    
    public List<Certificate> getCertificates() {
        return this.certificates;
    }
    
    public void setCertificates(final List<Certificate> certificates) {
        this.certificates = certificates;
    }
    
    public boolean isAcceptingPatients() {
        return this.isAcceptingPatients;
    }
    
    public void setAcceptingPatients(final boolean acceptingPatients) {
        this.isAcceptingPatients = acceptingPatients;
    }
}
