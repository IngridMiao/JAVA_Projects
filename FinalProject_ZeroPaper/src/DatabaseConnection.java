import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {
    private static Connection conn;

    private DatabaseConnection() {
        // private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        if (conn == null) {
            synchronized (DatabaseConnection.class) {
                if (conn == null) {
                    try {
                        String server = "jdbc:mysql://140.119.19.73:3315/";
                        String database = "110305075"; // change to your own database
                        String url = server + database + "?useSSL=false";
                        String username = "110305075"; // change to your own username
                        String password = "ku05g"; // change to your own password

                        conn = DriverManager.getConnection(url, username, password);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return conn;
    }
}


