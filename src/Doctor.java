import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Doctor Entity
 */
public class Doctor {
    private int doctorId;
    private int userId;
    private String fullName;
    private String specialty;
    private String clinicName;
    private String address;
    private String city;
    private String phoneNumber;
    private double consultationFees;
    private int yearsOfExperience;
    private String bio;
    private double rating;
    private int reviewCount;
    private boolean isAcceptingPatients;
    private List<Certificate> certificates;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Doctor() {
        this.certificates = new ArrayList<>();
        this.rating = 0.0;
        this.reviewCount = 0;
        this.isAcceptingPatients = true;
    }

    // Getters and Setters
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public double getConsultationFees() { return consultationFees; }
    public void setConsultationFees(double consultationFees) { this.consultationFees = consultationFees; }

    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }

    public boolean isAcceptingPatients() { return isAcceptingPatients; }
    public void setAcceptingPatients(boolean acceptingPatients) { isAcceptingPatients = acceptingPatients; }

    public List<Certificate> getCertificates() { return certificates; }
    public void setCertificates(List<Certificate> certificates) { this.certificates = certificates; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
