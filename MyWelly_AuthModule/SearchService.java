import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

// 
// Decompiled by Procyon v0.6.0
// 

class SearchService
{
    public List<Doctor> searchDoctors(final SearchFilters filters) throws SQLException {
        final List<Doctor> doctors = new ArrayList<Doctor>();
        final StringBuilder query = new StringBuilder("SELECT DISTINCT d.doctor_id, d.user_id, d.full_name, d.specialty, d.clinic_name, d.address, d.city, d.phone_number, d.consultation_fees, d.years_of_experience, d.bio, d.rating, d.review_count, d.is_accepting_patients FROM doctors d ");
        if (filters.getCertificateNames() != null && !filters.getCertificateNames().isEmpty()) {
            query.append("INNER JOIN certificates c ON d.doctor_id = c.doctor_id ");
        }
        query.append("WHERE d.is_accepting_patients = TRUE ");
        final List<Object> params = new ArrayList<Object>();
        if (filters.getSearchQuery() != null && !filters.getSearchQuery().trim().isEmpty()) {
            if (filters.getSearchType().equals("name")) {
                query.append("AND d.full_name LIKE ? ");
            }
            else {
                query.append("AND d.specialty LIKE ? ");
            }
            params.add("%" + filters.getSearchQuery().trim());
        }
        if (filters.getCity() != null && !filters.getCity().equals("All Cities")) {
            query.append("AND d.city = ? ");
            params.add(filters.getCity());
        }
        if (filters.getMinPrice() != null) {
            query.append("AND d.consultation_fees >= ? ");
            params.add(filters.getMinPrice());
        }
        if (filters.getMaxPrice() != null) {
            query.append("AND d.consultation_fees <= ? ");
            params.add(filters.getMaxPrice());
        }
        if (filters.getMinYearsExperience() != null) {
            query.append("AND d.years_of_experience >= ? ");
            params.add(filters.getMinYearsExperience());
        }
        if (filters.getMinRating() != null) {
            query.append("AND d.rating >= ? ");
            params.add(filters.getMinRating());
        }
        if (filters.getCertificateNames() != null && !filters.getCertificateNames().isEmpty()) {
            query.append("AND c.name IN (");
            for (int i = 0; i < filters.getCertificateNames().size(); ++i) {
                query.append((i > 0) ? ", ?" : "?");
                params.add(filters.getCertificateNames().get(i));
            }
            query.append(") ");
        }
        query.append("ORDER BY ");
        final String lowerCase = filters.getSortBy().toLowerCase();
        switch (lowerCase) {
            case "experience": {
                query.append("d.years_of_experience ");
                break;
            }
            case "fees": {
                query.append("d.consultation_fees ");
                break;
            }
            default: {
                query.append("d.rating ");
                break;
            }
        }
        query.append(filters.getSortOrder().equalsIgnoreCase("asc") ? "ASC" : "DESC");
        query.append(", d.review_count DESC");
        try (final Connection conn = DatabaseConnection.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            for (int j = 0; j < params.size(); ++j) {
                stmt.setObject(j + 1, params.get(j));
            }
            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                final Doctor doctor = new Doctor(rs.getLong("doctor_id"), rs.getLong("user_id"), rs.getString("full_name"), rs.getString("specialty"), rs.getString("clinic_name"), rs.getString("address"), rs.getString("city"), rs.getString("phone_number"), rs.getDouble("consultation_fees"), rs.getInt("years_of_experience"), rs.getString("bio"), rs.getDouble("rating"), rs.getInt("review_count"), rs.getBoolean("is_accepting_patients"));
                final ProfileService profileService = new ProfileService();
                doctor.setCertificates(profileService.getDoctorCertificates(doctor.getDoctorId()));
                doctors.add(doctor);
            }
        }
        return doctors;
    }
    
    public List<Laboratory> searchLaboratories(final String searchQuery, final String city) throws SQLException {
        final List<Laboratory> labs = new ArrayList<Laboratory>();
        final StringBuilder query = new StringBuilder("SELECT l.laboratory_id, l.user_id, l.laboratory_name, l.location, l.city, l.phone_number, l.email, l.services_offered, l.rating, l.review_count, l.accepts_walk_ins FROM laboratories l WHERE 1=1 ");
        final List<Object> params = new ArrayList<Object>();
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            query.append("AND l.laboratory_name LIKE ? ");
            params.add("%" + searchQuery.trim());
        }
        if (city != null && !city.equals("All Cities")) {
            query.append("AND l.city = ? ");
            params.add(city);
        }
        query.append("ORDER BY l.rating DESC, l.review_count DESC");
        try (final Connection conn = DatabaseConnection.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); ++i) {
                stmt.setObject(i + 1, params.get(i));
            }
            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                final Laboratory lab = new Laboratory(rs.getLong("laboratory_id"), rs.getLong("user_id"), rs.getString("laboratory_name"), rs.getString("location"), rs.getString("city"), rs.getString("phone_number"), rs.getString("email"), rs.getString("services_offered"), rs.getDouble("rating"), rs.getInt("review_count"), rs.getBoolean("accepts_walk_ins"));
                labs.add(lab);
            }
        }
        return labs;
    }
    
    public List<String> getAllCities() throws SQLException {
        final List<String> cities = new ArrayList<String>();
        cities.add("All Cities");
        final String query = "SELECT DISTINCT city FROM doctors WHERE city IS NOT NULL UNION SELECT DISTINCT city FROM laboratories WHERE city IS NOT NULL ORDER BY city";
        try (final Connection conn = DatabaseConnection.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query);
             final ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cities.add(rs.getString("city"));
            }
        }
        return cities;
    }
    
    public List<String> getAllCertificateTypes() throws SQLException {
        final List<String> certTypes = new ArrayList<String>();
        final String query = "SELECT DISTINCT name FROM certificates ORDER BY name";
        try (final Connection conn = DatabaseConnection.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query);
             final ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                certTypes.add(rs.getString("name"));
            }
        }
        return certTypes;
    }
}
