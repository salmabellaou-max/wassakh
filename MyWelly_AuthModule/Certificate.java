import java.time.LocalDate;

// 
// Decompiled by Procyon v0.6.0
// 

class Certificate
{
    private Long certId;
    private Long doctorId;
    private String name;
    private String issuingOrganization;
    private LocalDate issueDate;
    
    public Certificate(final Long certId, final Long doctorId, final String name, final String issuingOrganization, final LocalDate issueDate) {
        this.certId = certId;
        this.doctorId = doctorId;
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
    }
    
    public Long getCertId() {
        return this.certId;
    }
    
    public Long getDoctorId() {
        return this.doctorId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getIssuingOrganization() {
        return this.issuingOrganization;
    }
    
    public LocalDate getIssueDate() {
        return this.issueDate;
    }
    
    @Override
    public String toString() {
        return this.name + " - " + this.issuingOrganization + " (" + String.valueOf(this.issueDate);
    }
}
