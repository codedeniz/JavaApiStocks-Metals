import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClient {

    private static final String POLYGON_API_KEY = "oadtRso3pnRzCBnyV2Y7KIJDsW5lWowu"; //MY POLYGON API
    private static final String METALPRICE_API_KEY = "e3133b03041ceb9a35746d2b643ca4b6"; //MY METALPRICE API
    public static JsonObject getStockData(String stockSymbol) {
        HttpURLConnection connection = null;
        try {
            String urlString = "https://api.polygon.io/v2/aggs/ticker/" + stockSymbol.toUpperCase() + "/prev?apiKey=" + POLYGON_API_KEY;
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return JsonParser.parseString(response.toString()).getAsJsonObject();
        } catch (IOException e) {
            System.err.println("Error while fetching stock data: " + e.getMessage());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static JsonObject getMetalData(String baseCurrency, String[] metals) {
        HttpURLConnection connection = null;
        try {
            StringBuilder symbols = new StringBuilder();
            for (int i = 0; i < metals.length; i++) {
                symbols.append(metals[i].toUpperCase());
                if (i < metals.length - 1) {
                    symbols.append(",");
                }
            }

            String urlString = "https://api.metalpriceapi.com/v1/latest?api_key=" + METALPRICE_API_KEY +
                    "&base=" + baseCurrency.toUpperCase() +
                    "&currencies=" + symbols.toString();

            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Raw response from MetalPrice API: " + response);

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();

            if (jsonResponse.has("error")) {
                String errorMessage = jsonResponse.getAsJsonObject("error").get("message").getAsString();
                System.err.println("API error: " + errorMessage);
                return null;
            }

            return jsonResponse;
        } catch (IOException e) {
            System.err.println("Error while fetching metal data: " + e.getMessage());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}