import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String DB_URL = "jdbc:h2:~/test1";
    private static String DB_USER = "sa";
    private static String DB_PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName ("org.h2.Driver");
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection (DB_URL, DB_USER,DB_PASSWORD);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.commit();
            //Change this for exercise 3
            //connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                System.out.println("Closing connection...");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
