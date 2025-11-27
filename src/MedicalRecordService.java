import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordService {

    public static boolean addMedicalRecord(MedicalRecord record) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO medical_records (patient_id, appointment_id, record_type, " +
                    "title, description, file_url, added_by_type, added_by_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, record.getPatientId());
                if (record.getAppointmentId() != null) {
                    stmt.setInt(2, record.getAppointmentId());
                } else {
                    stmt.setNull(2, Types.INTEGER);
                }
                stmt.setString(3, record.getRecordType().name());
                stmt.setString(4, record.getTitle());
                stmt.setString(5, record.getDescription());
                stmt.setString(6, record.getFileUrl());
                stmt.setString(7, record.getAddedByType());
                stmt.setInt(8, record.getAddedById());
                return stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.err.println("Error adding medical record: " + e.getMessage());
        }
        return false;
    }

    public static List<MedicalRecord> getPatientRecords(int patientId) {
        List<MedicalRecord> records = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM medical_records WHERE patient_id = ? ORDER BY created_at DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, patientId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    MedicalRecord record = new MedicalRecord();
                    record.setRecordId(rs.getInt("record_id"));
                    record.setPatientId(rs.getInt("patient_id"));
                    record.setAppointmentId(rs.getObject("appointment_id", Integer.class));
                    record.setRecordType(RecordType.valueOf(rs.getString("record_type")));
                    record.setTitle(rs.getString("title"));
                    record.setDescription(rs.getString("description"));
                    record.setFileUrl(rs.getString("file_url"));
                    record.setAddedByType(rs.getString("added_by_type"));
                    record.setAddedById(rs.getInt("added_by_id"));
                    record.setCreatedAt(rs.getTimestamp("created_at"));
                    records.add(record);
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting medical records: " + e.getMessage());
        }
        return records;
    }
}
