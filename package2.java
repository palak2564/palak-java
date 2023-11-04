package usermanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PortfolioManagementSystem {
    public static void main(String[] args) {
        System.out.println("Portfolio Management System");
        System.out.println("1. Login");
        System.out.println("2. Create a new account");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        Connection con = null;
        switch (choice) {
            case 1:
                login(con, scanner);
                break;
            case 2:
                createAccount(con, scanner);
                break;
            case 3:
                System.out.println("Exiting the Portfolio Management System.");
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void login(Connection con, Scanner scanner) {
        try {
            System.out.print("Enter username: ");
            String usernameInput = scanner.next();
            System.out.print("Enter password: ");
            String passwordInput = scanner.next();

            String query = "SELECT username FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, usernameInput);
            pstmt.setString(2, passwordInput);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                System.out.println("Login successful. Welcome, " + username + ".");

            } else {
                System.out.println("Login failed. Please check your username and password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createAccount(Connection con, Scanner scanner) {
        try {
            System.out.print("Enter username: ");
            String usernameInput = scanner.next();
            System.out.print("Enter password: ");
            String passwordInput = scanner.next();

            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, usernameInput);
            pstmt.setString(2, passwordInput);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Account created successfully.");
            } else {
                System.out.println("Failed to create the account. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
