import java.sql.*;

public class DatabaseConnectionManager {
    private static Connection conn;

    public static void connect(String url, String user, String password) throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void close() throws SQLException {
        conn.close();
    }
}
