import java.time.LocalDateTime;

// 
// Decompiled by Procyon v0.6.0
// 

class MedicalRecord
{
    private Long recordId;
    private Long patientId;
    private Long appointmentId;
    private RecordType recordType;
    private String title;
    private String description;
    private String fileUrl;
    private ProviderType addedByType;
    private Long addedById;
    private LocalDateTime createdAt;
    
    public MedicalRecord(final Long recordId, final Long patientId, final Long appointmentId, final RecordType recordType, final String title, final String description, final String fileUrl, final ProviderType addedByType, final Long addedById, final LocalDateTime createdAt) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.recordType = recordType;
        this.title = title;
        this.description = description;
        this.fileUrl = fileUrl;
        this.addedByType = addedByType;
        this.addedById = addedById;
        this.createdAt = createdAt;
    }
    
    public Long getRecordId() {
        return this.recordId;
    }
    
    public Long getPatientId() {
        return this.patientId;
    }
    
    public RecordType getRecordType() {
        return this.recordType;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getFileUrl() {
        return this.fileUrl;
    }
    
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
