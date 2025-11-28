import java.util.ArrayList;
import java.util.List;

// 
// Decompiled by Procyon v0.6.0
// 

class SearchFilters
{
    private String searchQuery;
    private String searchType;
    private String city;
    private Double minPrice;
    private Double maxPrice;
    private Integer minYearsExperience;
    private Double minRating;
    private Boolean acceptingPatients;
    private List<String> certificateNames;
    private String sortBy;
    private String sortOrder;
    
    public SearchFilters() {
        this.searchType = "specialty";
        this.city = "All Cities";
        this.certificateNames = new ArrayList<String>();
        this.sortBy = "rating";
        this.sortOrder = "desc";
    }
    
    public String getSearchQuery() {
        return this.searchQuery;
    }
    
    public void setSearchQuery(final String searchQuery) {
        this.searchQuery = searchQuery;
    }
    
    public String getSearchType() {
        return this.searchType;
    }
    
    public void setSearchType(final String searchType) {
        this.searchType = searchType;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(final String city) {
        this.city = city;
    }
    
    public Double getMinPrice() {
        return this.minPrice;
    }
    
    public void setMinPrice(final Double minPrice) {
        this.minPrice = minPrice;
    }
    
    public Double getMaxPrice() {
        return this.maxPrice;
    }
    
    public void setMaxPrice(final Double maxPrice) {
        this.maxPrice = maxPrice;
    }
    
    public Integer getMinYearsExperience() {
        return this.minYearsExperience;
    }
    
    public void setMinYearsExperience(final Integer minYearsExperience) {
        this.minYearsExperience = minYearsExperience;
    }
    
    public Double getMinRating() {
        return this.minRating;
    }
    
    public void setMinRating(final Double minRating) {
        this.minRating = minRating;
    }
    
    public Boolean getAcceptingPatients() {
        return this.acceptingPatients;
    }
    
    public void setAcceptingPatients(final Boolean acceptingPatients) {
        this.acceptingPatients = acceptingPatients;
    }
    
    public List<String> getCertificateNames() {
        return this.certificateNames;
    }
    
    public void setCertificateNames(final List<String> certificateNames) {
        this.certificateNames = certificateNames;
    }
    
    public void addCertificateName(final String certName) {
        this.certificateNames.add(certName);
    }
    
    public String getSortBy() {
        return this.sortBy;
    }
    
    public void setSortBy(final String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortOrder() {
        return this.sortOrder;
    }
    
    public void setSortOrder(final String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public boolean hasActiveFilters() {
        return (this.city != null && !this.city.equals("All Cities")) || this.minPrice != null || this.maxPrice != null || this.minYearsExperience != null || this.minRating != null || this.acceptingPatients != null || (this.certificateNames != null && !this.certificateNames.isEmpty());
    }
}
