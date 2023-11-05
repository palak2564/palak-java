import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

abstract class Asset {
    protected String username;
    protected String name;
    protected String purchaseDate;
    protected int quantity;

    public Asset(String username, String name, String purchaseDate, int quantity) {
        this.username = username;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.quantity = quantity;
    }

    public abstract void addToPortfolio(Connection connection);

}

class Stock extends Asset {
    private String stockSymbol;
    public Stock(String username, String stockSymbol, String purchaseDate, int quantity) {
        super(username, "Stock", purchaseDate, quantity);
        this.stockSymbol = stockSymbol;
    }

    @Override
    public void addToPortfolio(Connection connection) {
        try {
            String addStockSQL = "INSERT INTO stocks (username, stock_symbol, purchase_date, quantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(addStockSQL)) {
                pstmt.setString(1, username);
                pstmt.setString(2, stockSymbol);
                pstmt.setString(3, purchaseDate);
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
}

class Bond extends Asset {
    private double faceValue;
    private double bondPrice;

    public Bond(String username, String name, String purchaseDate, double faceValue, double bondPrice, int quantity) {
        super(username, name, purchaseDate, quantity);
        this.faceValue = faceValue;
        this.bondPrice = bondPrice;
    }

    @Override
    public void addToPortfolio(Connection connection) {
        try {
            String addBondSQL = "INSERT INTO bonds (username, bond_name, purchase_date, face_value, bond_price, quantity) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(addBondSQL)) {
                pstmt.setString(1, username);
                pstmt.setString(2, name);
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
}

public class AssetManagement {
    public static void assetManagementMenu(Connection con, Scanner scanner, String usernameInput) throws SQLException {
        while (true) {
            System.out.println("Asset Management Menu");
            System.out.println("1. Add Stock");
            System.out.println("2. Add Bond");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStockToPortfolio(con, usernameInput, scanner);
                    break;
                case 2:
                    addBondToPortfolio(con, usernameInput, scanner);
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

    private static void addStockToPortfolio(Connection connection, String username, Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String stockSymbol = scanner.next();
        System.out.print("Enter purchase date (yyyy-MM-dd): ");
        String purchaseDate = scanner.next();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        Asset stock = new Stock(username, stockSymbol, purchaseDate, quantity);
        stock.addToPortfolio(connection);
    }

    private static void addBondToPortfolio(Connection connection, String username, Scanner scanner) {
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

        Asset bond = new Bond(username, bondName, purchaseDate, faceValue, bondPrice, quantity);
        bond.addToPortfolio(connection);
    }

    public static void main(String[] args) {
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);
        String usernameInput = "";

        try {
            assetManagementMenu(connection, scanner, usernameInput);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
