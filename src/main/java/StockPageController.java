import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.google.gson.JsonObject;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StockPageController {

    @FXML
    private TextField stockSymbolField;

    @FXML
    private TextArea resultArea;

    @FXML
    private void fetchStockData() {
        String stockSymbol = stockSymbolField.getText().trim();

        if (stockSymbol.isEmpty()) {
            resultArea.setText("Please enter a valid stock symbol.");
            return;
        }

        JsonObject stockData = APIClient.getStockData(stockSymbol);
        if (stockData != null) {
            try {
                if (stockData.has("results")) {
                    String closePrice = stockData.getAsJsonArray("results")
                            .get(0).getAsJsonObject()
                            .get("c").getAsString();
                    resultArea.setText("Stock: " + stockSymbol + "\nClose Price: $" + closePrice);
                } else {
                    resultArea.setText("Error: Stock data not available.");
                }
            } catch (Exception e) {
                resultArea.setText("Error processing stock data: " + e.getMessage());
            }
        } else {
            resultArea.setText("Error fetching stock data.");
        }
    }

    @FXML
    private void switchToMetalsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MetalsPage.fxml"));
            BorderPane metalsRoot = loader.load();
            Scene metalsScene = new Scene(metalsRoot);
            Stage currentStage = (Stage) resultArea.getScene().getWindow();
            currentStage.setScene(metalsScene);
        } catch (Exception e) {
            resultArea.setText("Error loading Metals page: " + e.getMessage());
        }
    }
}
