import java.sql.*;
import java.util.Scanner;

public class PortfolioManagementSystem {
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

            if (!tablesExist(con)) {
                createTables(con);
            }

            while (true) {
                System.out.println("Portfolio Management System");
                System.out.println("1. Login");
                System.out.println("2. Create a new account");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        login(con, scanner);
                        break;
                    case 2:
                        createAccount(con, scanner);
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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean tablesExist(Connection con) {
        try {
            DatabaseMetaData metadata = con.getMetaData();

            String[] tableNames = {"users", "user_details", "portfolios", "stocks", "bonds"};

            for (String tableName : tableNames) {
                ResultSet resultSet = metadata.getTables(null, null, tableName, null);
                if (!resultSet.next()) {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void createTables(Connection con) {
        try {
            String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255) DEFAULT 'default_password')";
            try (PreparedStatement pstmt = con.prepareStatement(createUserTableSQL)) {
                pstmt.executeUpdate();
            }

            String createUserDetailsTableSQL = "CREATE TABLE IF NOT EXISTS user_details (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), email VARCHAR(255), first_name VARCHAR(255), last_name VARCHAR(255), date_of_birth DATE, address VARCHAR(255), city VARCHAR(255), state VARCHAR(255), country VARCHAR(255), postal_code VARCHAR(255), phone_number VARCHAR(255))";
            try (PreparedStatement pstmt = con.prepareStatement(createUserDetailsTableSQL)) {
                pstmt.executeUpdate();
            }

            String createPortfoliosTableSQL = "CREATE TABLE IF NOT EXISTS portfolios (id INT AUTO_INCREMENT PRIMARY KEY, portfolio_name VARCHAR(255), username VARCHAR(255))";
            try (PreparedStatement pstmt = con.prepareStatement(createPortfoliosTableSQL)) {
                pstmt.executeUpdate();
            }

            String createStocksTableSQL = "CREATE TABLE IF NOT EXISTS stocks (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), stock_symbol VARCHAR(255), buy_date DATE, quantity INT)";
            try (PreparedStatement pstmt = con.prepareStatement(createStocksTableSQL)) {
                pstmt.executeUpdate();
            }

            String createBondsTableSQL = "CREATE TABLE IF NOT EXISTS bonds (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), bond_name VARCHAR(255), purchase_date DATE, face_value DOUBLE, bond_price DOUBLE, quantity INT)";
            try (PreparedStatement pstmt = con.prepareStatement(createBondsTableSQL)) {
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

    private static void userMenu(Connection con, Scanner scanner, String username) {
        while (true) {
            System.out.println("User Menu");
            System.out.println("1. Create a new Portfolio");
            System.out.println("2. View Portfolio");
            System.out.println("3. Calculate Profits and Losses");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createPortfolio(con, scanner, username);
                    assetManagementMenu(con, scanner, username);
                    break;
                case 2:
                    viewPortfolio(con, username, scanner);
                    break;
                case 3:
                    // SUMIT KA CODE


                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createPortfolio(Connection con, Scanner scanner, String username) {
        try {
            System.out.print("Enter the portfolio name: ");
            String portfolioName = scanner.next().trim();

            if (isPortfolioNameTaken(con, portfolioName)) {
                System.out.println("Portfolio name already exists. Please choose a different name.");
                return;
            }

            String insertPortfolioSQL = "INSERT INTO portfolios (portfolio_name, username) VALUES (?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertPortfolioSQL)) {
                pstmt.setString(1, portfolioName);
                pstmt.setString(2, username);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Portfolio created successfully!");
                } else {
                    System.out.println("Failed to create the portfolio.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void assetManagementMenu(Connection con, Scanner scanner, String usernameInput) {
        while (true) {
            System.out.println("Asset Management Menu");
            System.out.println("1. Add Stock");
            System.out.println("2. Add Bond");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStock(con, usernameInput, scanner);
                    break;
                case 2:
                    addBond(con, usernameInput, scanner);
                    break;
                case 3:
                    System.out.println("Exiting the Asset Management System.");
                    con.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStock(Connection connection, String username, Scanner scanner) {
        try {
            System.out.print("Enter stock symbol: ");
            String stockSymbol = scanner.next();
            System.out.print("Enter buy date (yyyy-MM-dd): ");
            String buyDate = scanner.next();
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();

            String addStockSQL = "INSERT INTO stocks (username, stock_symbol, buy_date, quantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(addStockSQL)) {
                pstmt.setString(1, username);
                pstmt.setString(2, stockSymbol);
                pstmt.setString(3, buyDate);
                pstmt.setInt(4, quantity);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Stock added to your portfolio!");
                } else {
                    System.out.println("Failed to add the stock.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addBond(Connection connection, String username, Scanner scanner) {
        try {
            System.out.print("Enter bond name: ");
            String bondName = scanner.next();
            System.out.print("Enter purchase date (yyyy-MM-dd): ");
            String purchaseDate = scanner.next();
            System.out.print("Enter face value: ");
            double faceValue = scanner.nextDouble();
            System.out.print("Enter bond price: ");
            double bondPrice = scanner.nextDouble();
            System.out.print("Enter bond quantity: ");
            int quantity = scanner.nextInt();

            String addBondSQL = "INSERT INTO bonds (username, bond_name, purchase_date, face_value, bond_price, quantity) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(addBondSQL)) {
                pstmt.setString(1, username);
                pstmt.setString(2, bondName);
                pstmt.setString(3, purchaseDate);
                pstmt.setDouble(4, faceValue);
                pstmt.setDouble(5, bondPrice);
                pstmt.setInt(6, quantity);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Bond added to your portfolio!");
                } else {
                    System.out.println("Failed to add the bond.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewPortfolio(Connection connection, String username, Scanner scanner) {
        System.out.print("Enter portfolio name: ");
        String portfolioName = scanner.next();

        String query = "SELECT * FROM portfolios WHERE portfolio_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, portfolioName);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String usernameInPortfolio = resultSet.getString("username");
                System.out.println("Portfolio Name: " + portfolioName);
                System.out.println("Username: " + usernameInPortfolio);
            } else {
                System.out.println("Portfolio not found.");
            }
        }
    }

    private static boolean isPortfolioNameTaken(Connection con, String portfolioName) {
        try {
            String checkPortfolioSQL = "SELECT COUNT(*) FROM portfolios WHERE LOWER(portfolio_name) = LOWER(?)";
            try (PreparedStatement pstmt = con.prepareStatement(checkPortfolioSQL)) {
                pstmt.setString(1, portfolioName.toLowerCase());
                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
