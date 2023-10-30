package database;

import java.sql.*;
class FirstJDBC {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db1";
            String username = "root";
            String password = "root";

            Connection con = DriverManager.getConnection(url, username, password);

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
