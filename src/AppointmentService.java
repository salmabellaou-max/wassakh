import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Appointment Service
 * FIXED: Appointment counts are now based on actual bookings, not default numbers
 */
public class AppointmentService {

    /**
     * Book a new appointment
     * FIXED: Creates actual appointment record
     */
    public static boolean bookAppointment(int patientId, int providerId, ProviderType providerType,
                                         Timestamp dateTime, String reason) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if doctor is accepting patients (if provider is doctor)
            if (providerType == ProviderType.DOCTOR) {
                String checkSql = "SELECT is_accepting_patients FROM doctors WHERE doctor_id = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, providerId);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next() && !rs.getBoolean("is_accepting_patients")) {
                        System.out.println("Doctor is not accepting new patients");
                        return false;
                    }
                }
            }

            // Create appointment
            String sql = "INSERT INTO appointments (patient_id, provider_id, provider_type, " +
                    "date_time, reason, status) VALUES (?, ?, ?, ?, ?, 'SCHEDULED')";

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, patientId);
                stmt.setInt(2, providerId);
                stmt.setString(3, providerType.name());
                stmt.setTimestamp(4, dateTime);
                stmt.setString(5, reason);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        System.out.println("Appointment booked successfully. ID: " + rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error booking appointment: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get appointments for a patient
     */
    public static List<Appointment> getPatientAppointments(int patientId) {
        List<Appointment> appointments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM appointments WHERE patient_id = ? ORDER BY date_time DESC";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, patientId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(rs.getInt("appointment_id"));
                    appointment.setPatientId(rs.getInt("patient_id"));
                    appointment.setProviderId(rs.getInt("provider_id"));
                    appointment.setProviderType(ProviderType.valueOf(rs.getString("provider_type")));
                    appointment.setDateTime(rs.getTimestamp("date_time"));
                    appointment.setReason(rs.getString("reason"));
                    appointment.setStatus(AppointmentStatus.valueOf(rs.getString("status")));
                    appointment.setCancellationReason(rs.getString("cancellation_reason"));
                    appointment.setCreatedAt(rs.getTimestamp("created_at"));
                    appointment.setLastModified(rs.getTimestamp("last_modified"));

                    appointments.add(appointment);
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting patient appointments: " + e.getMessage());
        }

        return appointments;
    }

    /**
     * Get appointment count for a patient
     * FIXED: Returns actual count from database
     */
    public static int getPatientAppointmentCount(int patientId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) as count FROM appointments WHERE patient_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, patientId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting appointment count: " + e.getMessage());
        }
        return 0; // Return 0 for new patients
    }

    /**
     * Get scheduled appointment count for a patient
     */
    public static int getScheduledAppointmentCount(int patientId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) as count FROM appointments " +
                    "WHERE patient_id = ? AND status = 'SCHEDULED'";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, patientId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting scheduled appointment count: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Get completed appointment count for a patient
     */
    public static int getCompletedAppointmentCount(int patientId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) as count FROM appointments " +
                    "WHERE patient_id = ? AND status = 'COMPLETED'";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, patientId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting completed appointment count: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Get appointments for a provider (doctor or lab)
     */
    public static List<Appointment> getProviderAppointments(int providerId, ProviderType providerType) {
        List<Appointment> appointments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM appointments WHERE provider_id = ? AND provider_type = ? " +
                    "ORDER BY date_time DESC";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, providerId);
                stmt.setString(2, providerType.name());
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(rs.getInt("appointment_id"));
                    appointment.setPatientId(rs.getInt("patient_id"));
                    appointment.setProviderId(rs.getInt("provider_id"));
                    appointment.setProviderType(ProviderType.valueOf(rs.getString("provider_type")));
                    appointment.setDateTime(rs.getTimestamp("date_time"));
                    appointment.setReason(rs.getString("reason"));
                    appointment.setStatus(AppointmentStatus.valueOf(rs.getString("status")));
                    appointment.setCancellationReason(rs.getString("cancellation_reason"));
                    appointment.setCreatedAt(rs.getTimestamp("created_at"));
                    appointment.setLastModified(rs.getTimestamp("last_modified"));

                    appointments.add(appointment);
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting provider appointments: " + e.getMessage());
        }

        return appointments;
    }

    /**
     * Cancel an appointment
     */
    public static boolean cancelAppointment(int appointmentId, String cancellationReason,
                                           boolean isByPatient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            AppointmentStatus newStatus = isByPatient ?
                    AppointmentStatus.CANCELLED_BY_PATIENT :
                    AppointmentStatus.CANCELLED_BY_DOCTOR;

            String sql = "UPDATE appointments SET status = ?, cancellation_reason = ? " +
                    "WHERE appointment_id = ? AND status = 'SCHEDULED'";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newStatus.name());
                stmt.setString(2, cancellationReason);
                stmt.setInt(3, appointmentId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Appointment cancelled successfully");
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error cancelling appointment: " + e.getMessage());
        }
        return false;
    }

    /**
     * Complete an appointment
     */
    public static boolean completeAppointment(int appointmentId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE appointments SET status = 'COMPLETED' " +
                    "WHERE appointment_id = ? AND status = 'SCHEDULED'";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, appointmentId);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            System.err.println("Error completing appointment: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get appointment by ID
     */
    public static Appointment getAppointmentById(int appointmentId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM appointments WHERE appointment_id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, appointmentId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(rs.getInt("appointment_id"));
                    appointment.setPatientId(rs.getInt("patient_id"));
                    appointment.setProviderId(rs.getInt("provider_id"));
                    appointment.setProviderType(ProviderType.valueOf(rs.getString("provider_type")));
                    appointment.setDateTime(rs.getTimestamp("date_time"));
                    appointment.setReason(rs.getString("reason"));
                    appointment.setStatus(AppointmentStatus.valueOf(rs.getString("status")));
                    appointment.setCancellationReason(rs.getString("cancellation_reason"));
                    appointment.setCreatedAt(rs.getTimestamp("created_at"));
                    appointment.setLastModified(rs.getTimestamp("last_modified"));

                    return appointment;
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting appointment: " + e.getMessage());
        }
        return null;
    }
}
