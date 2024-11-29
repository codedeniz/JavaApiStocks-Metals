import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MetalsPageController {

    @FXML
    private TextField metalField;

    @FXML
    private TextField currencyField;

    @FXML
    private TextArea resultArea;

    @FXML
    private Button backButton;

    @FXML
    private void fetchMetalData() {
        String metal = metalField.getText().trim();
        String currency = currencyField.getText().trim();

        if (metal.isEmpty() || currency.isEmpty()) {
            resultArea.setText("Please enter both metal and currency.");
            return;
        }

        JsonObject metalData = APIClient.getMetalData(currency, new String[]{metal});
        if (metalData != null) {
            try {

                System.out.println("Received Metal Data: " + metalData.toString());


                if (metalData.has("rates") && metalData.getAsJsonObject("rates").has(metal.toUpperCase())) {
                    double price = metalData.getAsJsonObject("rates").get(metal.toUpperCase()).getAsDouble();
                    resultArea.setText("Price of " + metal + " in " + currency.toUpperCase() + ": " + price);
                } else {
                    resultArea.setText("Error: No rate found for this metal.");
                }
            } catch (Exception e) {
                resultArea.setText("Error processing metal data: " + e.getMessage());
            }
        } else {
            resultArea.setText("Error fetching metal data.");
        }
    }

    @FXML
    private void goBackToStockPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StockPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) metalField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Stock Page");

        } catch (IOException e) {
            e.printStackTrace();
            resultArea.setText("Error loading Stock Page: " + e.getMessage());
        }
    }
}