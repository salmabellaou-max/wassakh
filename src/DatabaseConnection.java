import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Manager for MyWelly Application
 * Handles MySQL database connections
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/medical_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Lock1982?";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Connection pool (simple implementation)
    private static Connection connection = null;

    static {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DRIVER);
            System.out.println("MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection established");
            } catch (SQLException e) {
                System.err.println("Failed to connect to database!");
                System.err.println("URL: " + URL);
                System.err.println("Error: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    /**
     * Close the database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    /**
     * Test the database connection
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get database metadata for debugging
     */
    public static void printConnectionInfo() {
        try {
            Connection conn = getConnection();
            var metaData = conn.getMetaData();
            System.out.println("=== Database Connection Info ===");
            System.out.println("Database: " + metaData.getDatabaseProductName());
            System.out.println("Version: " + metaData.getDatabaseProductVersion());
            System.out.println("Driver: " + metaData.getDriverName());
            System.out.println("Driver Version: " + metaData.getDriverVersion());
            System.out.println("URL: " + metaData.getURL());
            System.out.println("User: " + metaData.getUserName());
            System.out.println("================================");
        } catch (SQLException e) {
            System.err.println("Error getting connection info: " + e.getMessage());
        }
    }
}
