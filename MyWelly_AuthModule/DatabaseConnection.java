import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

// 
// Decompiled by Procyon v0.6.0
// 

class DatabaseConnection
{
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/medical_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Lock1982?";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/medical_db?useSSL=false&serverTimezone=UTC", "root", "Lock1982?");
        }
        catch (final ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
}
