package database.setup;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String url = "jdbc:mysql://localhost:3306/portfoliomanagement";
    private static final String username = "root";
    private static final String password = "root";

    public static void createDatabaseAndTables() {
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement statement = con.createStatement();

            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS portfoliomanagement";
            statement.executeUpdate(createDatabaseSQL);

            String useDatabaseSQL = "USE portfoliomanagement";
            statement.execute(useDatabaseSQL);

            createTables(statement);

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Statement statement) throws Exception {
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (username VARCHAR(255), password VARCHAR(255) DEFAULT 'default_password')";
        statement.executeUpdate(createUserTableSQL);

        String createUserDetailsTableSQL = "CREATE TABLE IF NOT EXISTS user_details (username VARCHAR(255), email VARCHAR(255), first_name VARCHAR(255), last_name VARCHAR(255), date_of_birth DATE, address VARCHAR(255), city VARCHAR(255), state VARCHAR(255), country VARCHAR(255), postal_code VARCHAR(255), phone_number VARCHAR(255))";
        statement.executeUpdate(createUserDetailsTableSQL);

        String createPortfoliosTableSQL = "CREATE TABLE IF NOT EXISTS portfolios (portfolio_name VARCHAR(255), username VARCHAR(255))";
        statement.executeUpdate(createPortfoliosTableSQL);

        String createStocksTableSQL = "CREATE TABLE IF NOT EXISTS stocks (username VARCHAR(255), stock_symbol VARCHAR(255), buy_date DATE, quantity INT)";
        statement.executeUpdate(createStocksTableSQL);

        String createBondsTableSQL = "CREATE TABLE IF NOT EXISTS bonds (username VARCHAR(255), bond_name VARCHAR(255), purchase_date DATE, face_value DOUBLE, bond_price DOUBLE, quantity INT)";
        statement.executeUpdate(createBondsTableSQL);
    }
}
