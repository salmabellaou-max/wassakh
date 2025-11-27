import java.util.ArrayList;
import java.util.List;

/**
 * Search Filters for Doctor/Laboratory Search
 * Updated to filter by price and Moroccan cities only
 */
public class SearchFilters {
    private String searchQuery;
    private String searchType; // "DOCTOR" or "LABORATORY"
    private String city; // Moroccan cities only
    private Double minPrice;
    private Double maxPrice;
    private Integer minYearsExperience;
    private Double minRating;
    private Boolean acceptingPatients;
    private String specialty;
    private String sortBy; // "rating", "price", "experience"
    private String sortOrder; // "ASC" or "DESC"

    // Moroccan cities list
    public static final List<String> MOROCCAN_CITIES = new ArrayList<>();

    static {
        MOROCCAN_CITIES.add("Casablanca");
        MOROCCAN_CITIES.add("Rabat");
        MOROCCAN_CITIES.add("Fès");
        MOROCCAN_CITIES.add("Marrakech");
        MOROCCAN_CITIES.add("Agadir");
        MOROCCAN_CITIES.add("Tangier");
        MOROCCAN_CITIES.add("Meknès");
        MOROCCAN_CITIES.add("Oujda");
        MOROCCAN_CITIES.add("Kenitra");
        MOROCCAN_CITIES.add("Tétouan");
        MOROCCAN_CITIES.add("Safi");
        MOROCCAN_CITIES.add("Salé");
        MOROCCAN_CITIES.add("Mohammedia");
        MOROCCAN_CITIES.add("Khouribga");
        MOROCCAN_CITIES.add("El Jadida");
        MOROCCAN_CITIES.add("Béni Mellal");
        MOROCCAN_CITIES.add("Nador");
        MOROCCAN_CITIES.add("Taza");
        MOROCCAN_CITIES.add("Settat");
        MOROCCAN_CITIES.add("Larache");
        MOROCCAN_CITIES.add("Khemisset");
        MOROCCAN_CITIES.add("Guelmim");
        MOROCCAN_CITIES.add("Berrechid");
        MOROCCAN_CITIES.add("Essaouira");
        MOROCCAN_CITIES.add("Taroudant");
        MOROCCAN_CITIES.add("Tiznit");
        MOROCCAN_CITIES.add("Laâyoune");
        MOROCCAN_CITIES.add("Dakhla");
    }

    public SearchFilters() {
        this.sortBy = "rating"; // Default sort by rating
        this.sortOrder = "DESC";
    }

    // Getters and Setters
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

    public String getSearchType() { return searchType; }
    public void setSearchType(String searchType) { this.searchType = searchType; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getMinPrice() { return minPrice; }
    public void setMinPrice(Double minPrice) { this.minPrice = minPrice; }

    public Double getMaxPrice() { return maxPrice; }
    public void setMaxPrice(Double maxPrice) { this.maxPrice = maxPrice; }

    public Integer getMinYearsExperience() { return minYearsExperience; }
    public void setMinYearsExperience(Integer minYearsExperience) { this.minYearsExperience = minYearsExperience; }

    public Double getMinRating() { return minRating; }
    public void setMinRating(Double minRating) { this.minRating = minRating; }

    public Boolean getAcceptingPatients() { return acceptingPatients; }
    public void setAcceptingPatients(Boolean acceptingPatients) { this.acceptingPatients = acceptingPatients; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }

    public String getSortOrder() { return sortOrder; }
    public void setSortOrder(String sortOrder) { this.sortOrder = sortOrder; }
}
