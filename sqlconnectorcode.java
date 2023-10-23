import java.sql.*;

class FirstJDBC {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db1";
            String username = "root";
            String password = "root";

            java.sql.Connection con = DriverManager.getConnection(url, username, password);

            if (con.isClosed()) {
                System.out.println("Connection is closed");
            } else {
                System.out.println("Connected!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
