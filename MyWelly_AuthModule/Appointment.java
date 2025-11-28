import java.time.temporal.Temporal;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;

// 
// Decompiled by Procyon v0.6.0
// 

class Appointment
{
    private Long appointmentId;
    private Long patientId;
    private Long providerId;
    private ProviderType providerType;
    private LocalDateTime dateTime;
    private String reason;
    private AppointmentStatus status;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    
    public Appointment(final Long appointmentId, final Long patientId, final Long providerId, final ProviderType providerType, final LocalDateTime dateTime, final String reason, final AppointmentStatus status, final LocalDateTime createdAt, final LocalDateTime lastModified) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.providerId = providerId;
        this.providerType = providerType;
        this.dateTime = dateTime;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
    }
    
    public boolean canModify() {
        final long hoursUntilAppointment = ChronoUnit.HOURS.between(LocalDateTime.now(), this.dateTime);
        return hoursUntilAppointment >= 24L && this.status == AppointmentStatus.SCHEDULED;
    }
    
    public Long getAppointmentId() {
        return this.appointmentId;
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
    
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public AppointmentStatus getStatus() {
        return this.status;
    }
    
    public String getCancellationReason() {
        return this.cancellationReason;
    }
    
    public void setCancellationReason(final String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
}
