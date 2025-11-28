// 
// Decompiled by Procyon v0.6.0
// 

class Laboratory
{
    private Long laboratoryId;
    private Long userId;
    private String laboratoryName;
    private String location;
    private String city;
    private String phoneNumber;
    private String email;
    private String servicesOffered;
    private Double rating;
    private Integer reviewCount;
    private boolean acceptsWalkIns;
    
    public Laboratory(final Long laboratoryId, final Long userId, final String laboratoryName, final String location, final String city, final String phoneNumber, final String email, final String servicesOffered, final Double rating, final Integer reviewCount, final boolean acceptsWalkIns) {
        this.laboratoryId = laboratoryId;
        this.userId = userId;
        this.laboratoryName = laboratoryName;
        this.location = location;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.servicesOffered = servicesOffered;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.acceptsWalkIns = acceptsWalkIns;
    }
    
    public Long getLaboratoryId() {
        return this.laboratoryId;
    }
    
    public Long getUserId() {
        return this.userId;
    }
    
    public String getLaboratoryName() {
        return this.laboratoryName;
    }
    
    public void setLaboratoryName(final String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(final String location) {
        this.location = location;
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
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public String getServicesOffered() {
        return this.servicesOffered;
    }
    
    public void setServicesOffered(final String servicesOffered) {
        this.servicesOffered = servicesOffered;
    }
    
    public Double getRating() {
        return this.rating;
    }
    
    public Integer getReviewCount() {
        return this.reviewCount;
    }
    
    public boolean isAcceptsWalkIns() {
        return this.acceptsWalkIns;
    }
    
    public void setAcceptsWalkIns(final boolean acceptsWalkIns) {
        this.acceptsWalkIns = acceptsWalkIns;
    }
}
