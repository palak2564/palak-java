package usermanagement;
import java.sql.Connection;
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

        switch (choice) {
            case 1:
                login(con,scanner);
                break;
            case 2:
                createAccount(con,scanner);
                break;
            case 3:
                System.out.println("Exiting the Portfolio Management System.");
                con.close();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void login() {
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
                userMenu(con, scanner, username);
            } else {
                System.out.println("Login failed. Please check your username and password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    }

    private static void createAccount() {
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
                userMenu(con, scanner, username);
            } else {
                System.out.println("Login failed. Please check your username and password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    }
}
