import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Search Service for Doctors and Laboratories
 * FIXED: Now sorts by rating only, filters by price and Moroccan cities
 */
public class SearchService {

    /**
     * Search doctors with filters
     * FIXED: Results are sorted by rating (DESC) by default
     */
    public static List<Doctor> searchDoctors(SearchFilters filters) {
        List<Doctor> doctors = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder sql = new StringBuilder(
                    "SELECT d.*, " +
                            "(SELECT COUNT(*) FROM certificates c WHERE c.doctor_id = d.doctor_id) as cert_count " +
                            "FROM doctors d WHERE 1=1"
            );

            List<Object> params = new ArrayList<>();

            // Search query filter
            if (filters.getSearchQuery() != null && !filters.getSearchQuery().trim().isEmpty()) {
                sql.append(" AND (d.full_name LIKE ? OR d.specialty LIKE ? OR d.clinic_name LIKE ?)");
                String searchPattern = "%" + filters.getSearchQuery() + "%";
                params.add(searchPattern);
                params.add(searchPattern);
                params.add(searchPattern);
            }

            // City filter (Moroccan cities only)
            if (filters.getCity() != null && !filters.getCity().trim().isEmpty()) {
                sql.append(" AND d.city = ?");
                params.add(filters.getCity());
            }

            // Specialty filter
            if (filters.getSpecialty() != null && !filters.getSpecialty().trim().isEmpty()) {
                sql.append(" AND d.specialty = ?");
                params.add(filters.getSpecialty());
            }

            // Price range filter (FIXED: using price instead of certificates)
            if (filters.getMinPrice() != null) {
                sql.append(" AND d.consultation_fees >= ?");
                params.add(filters.getMinPrice());
            }
            if (filters.getMaxPrice() != null) {
                sql.append(" AND d.consultation_fees <= ?");
                params.add(filters.getMaxPrice());
            }

            // Years of experience filter
            if (filters.getMinYearsExperience() != null) {
                sql.append(" AND d.years_of_experience >= ?");
                params.add(filters.getMinYearsExperience());
            }

            // Rating filter
            if (filters.getMinRating() != null) {
                sql.append(" AND d.rating >= ?");
                params.add(filters.getMinRating());
            }

            // Accepting patients filter
            if (filters.getAcceptingPatients() != null && filters.getAcceptingPatients()) {
                sql.append(" AND d.is_accepting_patients = TRUE");
            }

            // FIXED: Sort by rating only (DESC - highest rating first)
            sql.append(" ORDER BY d.rating DESC, d.review_count DESC");

            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                // Set parameters
                for (int i = 0; i < params.size(); i++) {
                    stmt.setObject(i + 1, params.get(i));
                }

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Doctor doctor = new Doctor();
                    doctor.setDoctorId(rs.getInt("doctor_id"));
                    doctor.setUserId(rs.getInt("user_id"));
                    doctor.setFullName(rs.getString("full_name"));
                    doctor.setSpecialty(rs.getString("specialty"));
                    doctor.setClinicName(rs.getString("clinic_name"));
                    doctor.setAddress(rs.getString("address"));
                    doctor.setCity(rs.getString("city"));
                    doctor.setPhoneNumber(rs.getString("phone_number"));
                    doctor.setConsultationFees(rs.getDouble("consultation_fees"));
                    doctor.setYearsOfExperience(rs.getInt("years_of_experience"));
                    doctor.setBio(rs.getString("bio"));
                    doctor.setRating(rs.getDouble("rating"));
                    doctor.setReviewCount(rs.getInt("review_count"));
                    doctor.setAcceptingPatients(rs.getBoolean("is_accepting_patients"));

                    doctors.add(doctor);
                }
            }
        } catch (Exception e) {
            System.err.println("Error searching doctors: " + e.getMessage());
            e.printStackTrace();
        }

        return doctors;
    }

    /**
     * Search laboratories
     */
    public static List<Laboratory> searchLaboratories(String searchQuery, String city) {
        List<Laboratory> laboratories = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM laboratories WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                sql.append(" AND (laboratory_name LIKE ? OR services_offered LIKE ?)");
                String searchPattern = "%" + searchQuery + "%";
                params.add(searchPattern);
                params.add(searchPattern);
            }

            if (city != null && !city.trim().isEmpty()) {
                sql.append(" AND city = ?");
                params.add(city);
            }

            // Sort by rating
            sql.append(" ORDER BY rating DESC, review_count DESC");

            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    stmt.setObject(i + 1, params.get(i));
                }

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Laboratory lab = new Laboratory();
                    lab.setLaboratoryId(rs.getInt("laboratory_id"));
                    lab.setUserId(rs.getInt("user_id"));
                    lab.setLaboratoryName(rs.getString("laboratory_name"));
                    lab.setLocation(rs.getString("location"));
                    lab.setCity(rs.getString("city"));
                    lab.setPhoneNumber(rs.getString("phone_number"));
                    lab.setEmail(rs.getString("email"));
                    lab.setServicesOffered(rs.getString("services_offered"));
                    lab.setRating(rs.getDouble("rating"));
                    lab.setReviewCount(rs.getInt("review_count"));
                    lab.setAcceptsWalkIns(rs.getBoolean("accepts_walk_ins"));

                    laboratories.add(lab);
                }
            }
        } catch (Exception e) {
            System.err.println("Error searching laboratories: " + e.getMessage());
        }

        return laboratories;
    }

    /**
     * Get all Moroccan cities
     */
    public static List<String> getAllCities() {
        return new ArrayList<>(SearchFilters.MOROCCAN_CITIES);
    }

    /**
     * Get all specialties
     */
    public static List<String> getAllSpecialties() {
        List<String> specialties = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT DISTINCT specialty FROM doctors ORDER BY specialty";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    specialties.add(rs.getString("specialty"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting specialties: " + e.getMessage());
        }
        return specialties;
    }

    /**
     * Get doctor by ID with certificates
     */
    public static Doctor getDoctorById(int doctorId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, doctorId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Doctor doctor = new Doctor();
                    doctor.setDoctorId(rs.getInt("doctor_id"));
                    doctor.setUserId(rs.getInt("user_id"));
                    doctor.setFullName(rs.getString("full_name"));
                    doctor.setSpecialty(rs.getString("specialty"));
                    doctor.setClinicName(rs.getString("clinic_name"));
                    doctor.setAddress(rs.getString("address"));
                    doctor.setCity(rs.getString("city"));
                    doctor.setPhoneNumber(rs.getString("phone_number"));
                    doctor.setConsultationFees(rs.getDouble("consultation_fees"));
                    doctor.setYearsOfExperience(rs.getInt("years_of_experience"));
                    doctor.setBio(rs.getString("bio"));
                    doctor.setRating(rs.getDouble("rating"));
                    doctor.setReviewCount(rs.getInt("review_count"));
                    doctor.setAcceptingPatients(rs.getBoolean("is_accepting_patients"));

                    // Load certificates
                    doctor.setCertificates(getCertificatesForDoctor(doctorId));

                    return doctor;
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting doctor: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get certificates for a doctor
     */
    private static List<Certificate> getCertificatesForDoctor(int doctorId) {
        List<Certificate> certificates = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM certificates WHERE doctor_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, doctorId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Certificate cert = new Certificate();
                    cert.setCertId(rs.getInt("cert_id"));
                    cert.setDoctorId(rs.getInt("doctor_id"));
                    cert.setName(rs.getString("name"));
                    cert.setIssuingOrganization(rs.getString("issuing_organization"));
                    cert.setIssueDate(rs.getDate("issue_date"));
                    certificates.add(cert);
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting certificates: " + e.getMessage());
        }
        return certificates;
    }
}
