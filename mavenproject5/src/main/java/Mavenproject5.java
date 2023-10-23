import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Mavenproject5 {
    public static void main(String[] args) {
        try {
            // Your Alpha Vantage API key (replace with your own)
            String apiKey = "YOUR_ALPHA_VANTAGE_API_KEY";

            // Symbol of the stock you want to fetch (e.g., AAPL for Apple Inc.)
            String symbol = "IBM";

            // Create the API URL for fetching stock quote
            String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey="
                    + apiKey;

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

            // Parse the JSON response and extract LTP
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(response.toString()).getAsJsonObject();
            JsonObject globalQuote = json.getAsJsonObject("Global Quote");
            if (globalQuote != null) {
                double ltp = globalQuote.get("05. price").getAsDouble();
                System.out.println("Last Trade Price (LTP): " + ltp);
            } else {
                System.out.println("LTP data not found in the response.");
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
