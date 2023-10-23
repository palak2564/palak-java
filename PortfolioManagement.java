//code to insert data into sql tables
import java.sql.*;
import java.io.*

public class PortfolioManagement {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/portfoliomanagement";
            String username = "root";
            String password = "root";
            Connection con = DriverManager.getConnection(url, username, password);

            String query = "INSERT INTO user_details (username, password, email, first_name, last_name, date_of_birth, address, city, state, country, postal_code, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter username:");
            String usernameInput = br.readLine();

            System.out.println("Enter password:");
            String passwordInput = br.readLine();

            System.out.println("Enter email:");
            String emailInput = br.readLine();

            System.out.println("Enter first name:");
            String firstNameInput = br.readLine();

            System.out.println("Enter last name:");
            String lastNameInput = br.readLine();

            System.out.println("Enter date of birth (yyyy-MM-dd):");
            String dobInput = br.readLine();

            System.out.println("Enter address:");
            String addressInput = br.readLine();

            System.out.println("Enter city:");
            String cityInput = br.readLine();

            System.out.println("Enter state:");
            String stateInput = br.readLine();

            System.out.println("Enter country:");
            String countryInput = br.readLine();

            System.out.println("Enter postal code:");
            String postalCodeInput = br.readLine();

            System.out.println("Enter phone number:");
            String phoneNumberInput = br.readLine();

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
            } else {
                System.out.println("Failed to insert user details.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
