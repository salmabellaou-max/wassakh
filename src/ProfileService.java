import java.sql.*;

/**
 * Profile Service for managing user profiles
 * FIXED: Proper profile information retrieval
 */
public class ProfileService {

    /**
     * Get patient profile by user ID
     */
    public static Patient getPatientByUserId(int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM patients WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Patient patient = new Patient();
                    patient.setPatientId(rs.getInt("patient_id"));
                    patient.setUserId(rs.getInt("user_id"));
                    patient.setFullName(rs.getString("full_name"));
                    patient.setDateOfBirth(rs.getDate("date_of_birth"));
                    patient.setGender(rs.getString("gender"));
                    patient.setIdNumber(rs.getString("id_number"));
                    patient.setPhoneNumber(rs.getString("phone_number"));
                    patient.setPersonalEmail(rs.getString("personal_email"));
                    patient.setNoShowCount(rs.getInt("no_show_count"));
                    patient.setCreatedAt(rs.getTimestamp("created_at"));
                    patient.setUpdatedAt(rs.getTimestamp("updated_at"));

                    return patient;
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting patient profile: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get doctor profile by user ID
     */
    public static Doctor getDoctorByUserId(int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM doctors WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
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
                    doctor.setCreatedAt(rs.getTimestamp("created_at"));
                    doctor.setUpdatedAt(rs.getTimestamp("updated_at"));

                    return doctor;
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting doctor profile: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get laboratory profile by user ID
     */
    public static Laboratory getLaboratoryByUserId(int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM laboratories WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
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
                    lab.setCreatedAt(rs.getTimestamp("created_at"));
                    lab.setUpdatedAt(rs.getTimestamp("updated_at"));

                    return lab;
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting laboratory profile: " + e.getMessage());
        }
        return null;
    }

    /**
     * Create or update patient profile
     * FIXED: Gender validation - only Male or Female
     */
    public static boolean savePatientProfile(Patient patient) {
        // Validate gender
        if (!patient.getGender().equals("Male") && !patient.getGender().equals("Female")) {
            System.err.println("Invalid gender. Must be 'Male' or 'Female'");
            return false;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkSql = "SELECT patient_id FROM patients WHERE user_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, patient.getUserId());
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Update existing profile
                    String updateSql = "UPDATE patients SET full_name = ?, date_of_birth = ?, " +
                            "gender = ?, id_number = ?, phone_number = ?, personal_email = ? " +
                            "WHERE user_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, patient.getFullName());
                        updateStmt.setDate(2, patient.getDateOfBirth());
                        updateStmt.setString(3, patient.getGender());
                        updateStmt.setString(4, patient.getIdNumber());
                        updateStmt.setString(5, patient.getPhoneNumber());
                        updateStmt.setString(6, patient.getPersonalEmail());
                        updateStmt.setInt(7, patient.getUserId());
                        return updateStmt.executeUpdate() > 0;
                    }
                } else {
                    // Insert new profile
                    String insertSql = "INSERT INTO patients (user_id, full_name, date_of_birth, " +
                            "gender, id_number, phone_number, personal_email) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, patient.getUserId());
                        insertStmt.setString(2, patient.getFullName());
                        insertStmt.setDate(3, patient.getDateOfBirth());
                        insertStmt.setString(4, patient.getGender());
                        insertStmt.setString(5, patient.getIdNumber());
                        insertStmt.setString(6, patient.getPhoneNumber());
                        insertStmt.setString(7, patient.getPersonalEmail());
                        return insertStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error saving patient profile: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create or update doctor profile
     */
    public static boolean saveDoctorProfile(Doctor doctor) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkSql = "SELECT doctor_id FROM doctors WHERE user_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, doctor.getUserId());
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Update existing profile
                    String updateSql = "UPDATE doctors SET full_name = ?, specialty = ?, clinic_name = ?, " +
                            "address = ?, city = ?, phone_number = ?, consultation_fees = ?, " +
                            "years_of_experience = ?, bio = ?, is_accepting_patients = ? WHERE user_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, doctor.getFullName());
                        updateStmt.setString(2, doctor.getSpecialty());
                        updateStmt.setString(3, doctor.getClinicName());
                        updateStmt.setString(4, doctor.getAddress());
                        updateStmt.setString(5, doctor.getCity());
                        updateStmt.setString(6, doctor.getPhoneNumber());
                        updateStmt.setDouble(7, doctor.getConsultationFees());
                        updateStmt.setInt(8, doctor.getYearsOfExperience());
                        updateStmt.setString(9, doctor.getBio());
                        updateStmt.setBoolean(10, doctor.isAcceptingPatients());
                        updateStmt.setInt(11, doctor.getUserId());
                        return updateStmt.executeUpdate() > 0;
                    }
                } else {
                    // Insert new profile
                    String insertSql = "INSERT INTO doctors (user_id, full_name, specialty, clinic_name, " +
                            "address, city, phone_number, consultation_fees, years_of_experience, bio, " +
                            "is_accepting_patients) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, doctor.getUserId());
                        insertStmt.setString(2, doctor.getFullName());
                        insertStmt.setString(3, doctor.getSpecialty());
                        insertStmt.setString(4, doctor.getClinicName());
                        insertStmt.setString(5, doctor.getAddress());
                        insertStmt.setString(6, doctor.getCity());
                        insertStmt.setString(7, doctor.getPhoneNumber());
                        insertStmt.setDouble(8, doctor.getConsultationFees());
                        insertStmt.setInt(9, doctor.getYearsOfExperience());
                        insertStmt.setString(10, doctor.getBio());
                        insertStmt.setBoolean(11, doctor.isAcceptingPatients());
                        return insertStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error saving doctor profile: " + e.getMessage());
        }
        return false;
    }

    /**
     * Create or update laboratory profile
     */
    public static boolean saveLaboratoryProfile(Laboratory lab) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkSql = "SELECT laboratory_id FROM laboratories WHERE user_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, lab.getUserId());
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Update existing profile
                    String updateSql = "UPDATE laboratories SET laboratory_name = ?, location = ?, " +
                            "city = ?, phone_number = ?, email = ?, services_offered = ?, " +
                            "accepts_walk_ins = ? WHERE user_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, lab.getLaboratoryName());
                        updateStmt.setString(2, lab.getLocation());
                        updateStmt.setString(3, lab.getCity());
                        updateStmt.setString(4, lab.getPhoneNumber());
                        updateStmt.setString(5, lab.getEmail());
                        updateStmt.setString(6, lab.getServicesOffered());
                        updateStmt.setBoolean(7, lab.isAcceptsWalkIns());
                        updateStmt.setInt(8, lab.getUserId());
                        return updateStmt.executeUpdate() > 0;
                    }
                } else {
                    // Insert new profile
                    String insertSql = "INSERT INTO laboratories (user_id, laboratory_name, location, " +
                            "city, phone_number, email, services_offered, accepts_walk_ins) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, lab.getUserId());
                        insertStmt.setString(2, lab.getLaboratoryName());
                        insertStmt.setString(3, lab.getLocation());
                        insertStmt.setString(4, lab.getCity());
                        insertStmt.setString(5, lab.getPhoneNumber());
                        insertStmt.setString(6, lab.getEmail());
                        insertStmt.setString(7, lab.getServicesOffered());
                        insertStmt.setBoolean(8, lab.isAcceptsWalkIns());
                        return insertStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error saving laboratory profile: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get user entity by ID
     */
    public static UserEntity getUserById(int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    UserEntity user = new UserEntity();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setUserType(UserType.valueOf(rs.getString("user_type")));
                    user.setVerified(rs.getBoolean("is_verified"));
                    user.setActive(rs.getBoolean("is_active"));
                    user.setAccountLocked(rs.getBoolean("account_locked"));
                    user.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    user.setLastLogin(rs.getTimestamp("last_login"));

                    return user;
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting user: " + e.getMessage());
        }
        return null;
    }
}
