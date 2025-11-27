import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {

    public static boolean addReview(Review review) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO reviews (patient_id, provider_id, provider_type, " +
                    "appointment_id, rating, feedback) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, review.getPatientId());
                stmt.setInt(2, review.getProviderId());
                stmt.setString(3, review.getProviderType().name());
                if (review.getAppointmentId() != null) {
                    stmt.setInt(4, review.getAppointmentId());
                } else {
                    stmt.setNull(4, Types.INTEGER);
                }
                stmt.setInt(5, review.getRating());
                stmt.setString(6, review.getFeedback());
                return stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.err.println("Error adding review: " + e.getMessage());
        }
        return false;
    }

    public static List<Review> getProviderReviews(int providerId, ProviderType providerType) {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM reviews WHERE provider_id = ? AND provider_type = ? " +
                    "ORDER BY created_at DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, providerId);
                stmt.setString(2, providerType.name());
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Review review = new Review();
                    review.setReviewId(rs.getInt("review_id"));
                    review.setPatientId(rs.getInt("patient_id"));
                    review.setProviderId(rs.getInt("provider_id"));
                    review.setProviderType(ProviderType.valueOf(rs.getString("provider_type")));
                    review.setAppointmentId(rs.getObject("appointment_id", Integer.class));
                    review.setRating(rs.getInt("rating"));
                    review.setFeedback(rs.getString("feedback"));
                    review.setCreatedAt(rs.getTimestamp("created_at"));
                    reviews.add(review);
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting reviews: " + e.getMessage());
        }
        return reviews;
    }
}
