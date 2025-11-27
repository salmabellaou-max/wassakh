import java.sql.Timestamp;

public class MedicalRecord {
    private int recordId;
    private int patientId;
    private Integer appointmentId;
    private RecordType recordType;
    private String title;
    private String description;
    private String fileUrl;
    private String addedByType;
    private int addedById;
    private Timestamp createdAt;

    public MedicalRecord() {}

    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public Integer getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Integer appointmentId) { this.appointmentId = appointmentId; }

    public RecordType getRecordType() { return recordType; }
    public void setRecordType(RecordType recordType) { this.recordType = recordType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public String getAddedByType() { return addedByType; }
    public void setAddedByType(String addedByType) { this.addedByType = addedByType; }

    public int getAddedById() { return addedById; }
    public void setAddedById(int addedById) { this.addedById = addedById; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
