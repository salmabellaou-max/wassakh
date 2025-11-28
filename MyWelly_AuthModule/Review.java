import java.time.LocalDateTime;

// 
// Decompiled by Procyon v0.6.0
// 

class Review
{
    private Long reviewId;
    private Long patientId;
    private Long providerId;
    private ProviderType providerType;
    private Long appointmentId;
    private Integer rating;
    private String feedback;
    private LocalDateTime createdAt;
    
    public Review(final Long reviewId, final Long patientId, final Long providerId, final ProviderType providerType, final Long appointmentId, final Integer rating, final String feedback, final LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.patientId = patientId;
        this.providerId = providerId;
        this.providerType = providerType;
        this.appointmentId = appointmentId;
        this.rating = rating;
        this.feedback = feedback;
        this.createdAt = createdAt;
    }
    
    public Long getReviewId() {
        return this.reviewId;
    }
    
    public Long getPatientId() {
        return this.patientId;
    }
    
    public Long getProviderId() {
        return this.providerId;
    }
    
    public ProviderType getProviderType() {
        return this.providerType;
    }
    
    public Integer getRating() {
        return this.rating;
    }
    
    public String getFeedback() {
        return this.feedback;
    }
    
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
