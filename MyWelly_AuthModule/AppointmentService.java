import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

class AppointmentService {

    public Long bookAppointment(Long patientId, Long entityId, ProviderType providerType,
                               LocalDateTime appointmentDateTime, String reason) throws SQLException {
        // TODO: Decompilation failed - implementation needs to be recovered
        throw new UnsupportedOperationException("Method not yet decompiled");
    }

    public boolean cancelAppointment(Long appointmentId, String cancellationReason,
                                    Long userId) throws SQLException {
        // TODO: Decompilation failed - implementation needs to be recovered
        throw new UnsupportedOperationException("Method not yet decompiled");
    }

    public Appointment getAppointmentById(Long appointmentId) throws SQLException {
        // TODO: Decompilation failed - implementation needs to be recovered
        throw new UnsupportedOperationException("Method not yet decompiled");
    }

    public List<Appointment> getPatientAppointments(Long patientId) throws SQLException {
        // TODO: Decompilation failed - implementation needs to be recovered
        throw new UnsupportedOperationException("Method not yet decompiled");
    }
}
