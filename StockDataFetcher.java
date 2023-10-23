import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StockDataFetcher {
    public static void main(String[] args) {
        try {
            // Your Alpha Vantage API key (replace with your own)
            String apiKey = "YOUR_API_KEY_HERE";

            // Symbol of the stock you want to fetch (e.g., AAPL for Apple Inc.)
            String symbol = "AAPL";

            // Create the API URL
            String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
                    + symbol + "&interval=1min&apikey=" + apiKey;

            // Send an HTTP GET request to the API URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Print the JSON response (you can parse it for specific data)
            System.out.println(response.toString());

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}