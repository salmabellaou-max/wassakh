import java.sql.Date;
import java.sql.Timestamp;

/**
 * Certificate Entity for Doctors
 */
public class Certificate {
    private int certId;
    private int doctorId;
    private String name;
    private String issuingOrganization;
    private Date issueDate;
    private Timestamp createdAt;

    // Constructors
    public Certificate() {}

    public Certificate(int certId, int doctorId, String name, String issuingOrganization, Date issueDate) {
        this.certId = certId;
        this.doctorId = doctorId;
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
    }

    // Getters and Setters
    public int getCertId() { return certId; }
    public void setCertId(int certId) { this.certId = certId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIssuingOrganization() { return issuingOrganization; }
    public void setIssuingOrganization(String issuingOrganization) { this.issuingOrganization = issuingOrganization; }

    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
