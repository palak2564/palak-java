package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FirstJDBC {
    public static void main(String[] args) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/portfoliomanagement";
            String username = "root";
            String password = "root";
            con = DriverManager.getConnection(url, username, password);

            if (con.isClosed()) {
                System.out.println("Connection is closed");
            } else {
                System.out.println("Connected!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database");
        } finally {
            try {
                if (con != null) {
                    con.close(); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error while closing the connection");
            }
        }
    }
}
