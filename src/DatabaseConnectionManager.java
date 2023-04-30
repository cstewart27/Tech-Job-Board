import java.sql.*;

public class DatabaseConnectionManager {
    public static final String url = "jdbc:mysql80://localhost:3306/JobListingDatabase";
    public static final String usernameToDatabase = "root";
    public static final String passwordToDatabase = "1723";
    private static Connection conn;

    public static void connect(String url, String user, String password) throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
    }

    public static Connection getConnection(String url, String usernameToDatabase, String passwordToDatabase) {
        return conn;
    }

    public static void close() throws SQLException {
        conn.close();
    }
}
