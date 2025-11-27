import java.sql.Timestamp;

/**
 * Laboratory Entity
 */
public class Laboratory {
    private int laboratoryId;
    private int userId;
    private String laboratoryName;
    private String location;
    private String city;
    private String phoneNumber;
    private String email;
    private String servicesOffered;
    private double rating;
    private int reviewCount;
    private boolean acceptsWalkIns;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Laboratory() {
        this.rating = 0.0;
        this.reviewCount = 0;
        this.acceptsWalkIns = true;
    }

    // Getters and Setters
    public int getLaboratoryId() { return laboratoryId; }
    public void setLaboratoryId(int laboratoryId) { this.laboratoryId = laboratoryId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getLaboratoryName() { return laboratoryName; }
    public void setLaboratoryName(String laboratoryName) { this.laboratoryName = laboratoryName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getServicesOffered() { return servicesOffered; }
    public void setServicesOffered(String servicesOffered) { this.servicesOffered = servicesOffered; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }

    public boolean isAcceptsWalkIns() { return acceptsWalkIns; }
    public void setAcceptsWalkIns(boolean acceptsWalkIns) { this.acceptsWalkIns = acceptsWalkIns; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
