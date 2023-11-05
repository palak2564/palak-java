package usermanagement;

import java.sql.Connection;
import java.sql.DriverManager;
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
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/portfoliomanagement", "root", "root");
            switch (choice) {
                case 1:
                    login(con, scanner);
                    break;
                case 2:
                    createAccount(con, scanner);
                    break;
                case 3:
                    System.out.println("Exiting the Portfolio Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void login(Connection con, Scanner scanner) {
        try {
            System.out.print("Enter username: ");
            String usernameInput = scanner.next();
            System.out.print("Enter password: ");
            String passwordInput = scanner.next();

            String query = "SELECT username FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, usernameInput);
                pstmt.setString(2, passwordInput);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    System.out.println("Login successful. Welcome, " + username + ".");
                } else {
                    System.out.println("Login failed. Please check your username and password.");
                }
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
             System.out.print("Enter email: ");
            String emailInput = scanner.next();
            System.out.print("Enter first name: ");
            String firstNameInput = scanner.next();
            System.out.print("Enter last name: ");
            String lastNameInput = scanner.next();
            System.out.print("Enter date of birth (yyyy-MM-dd): ");
            String dobInput = scanner.next();
            System.out.print("Enter address: ");
            String addressInput = scanner.next();
            System.out.print("Enter city: ");
            String cityInput = scanner.next();
            System.out.print("Enter state: ");
            String stateInput = scanner.next();
            System.out.print("Enter country: ");
            String countryInput = scanner.next();
            System.out.print("Enter postal code: ");
            String postalCodeInput = scanner.next();
            System.out.print("Enter phone number: ");
            String phoneNumberInput = scanner.next();

            String insertUserDetailsSQL = "INSERT INTO user_details (username, password, email, first_name, last_name, date_of_birth, address, city, state, country, postal_code, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertUserDetailsSQL)) {
                pstmt.setString(1, usernameInput);
                pstmt.setString(2, passwordInput);
                pstmt.setString(3, emailInput);
                pstmt.setString(4, firstNameInput);
                pstmt.setString(5, lastNameInput);
                pstmt.setString(6, dobInput);
                pstmt.setString(7, addressInput);
                pstmt.setString(8, cityInput);
                pstmt.setString(9, stateInput);
                pstmt.setString(10, countryInput);
                pstmt.setString(11, postalCodeInput);
                pstmt.setString(12, phoneNumberInput);

            String insertUserDetailsSQL = "INSERT INTO user_details (username, password, email, first_name, last_name, date_of_birth, address, city, state, country, postal_code, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertUserDetailsSQL)) {
                pstmt.setString(1, usernameInput);
                pstmt.setString(2, passwordInput);
                // ... set other parameter values

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User details inserted successfully!");

                    System.out.print("Enter your username: ");
                    String username = scanner.next();
                    System.out.print("Enter your password: ");
                    String password = scanner.next();

                    String insertUserSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
                    try (PreparedStatement userPstmt = con.prepareStatement(insertUserSQL)) {
                        userPstmt.setString(1, username);
                        userPstmt.setString(2, password);

                        int userRowsAffected = userPstmt.executeUpdate();

                        if (userRowsAffected > 0) {
                            System.out.println("Account created successfully!");
                        } else {
                            System.out.println("Failed to create the account.");
                        }
                    }
                } else {
                    System.out.println("Failed to insert user details.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
