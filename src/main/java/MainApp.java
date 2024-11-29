import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the initial FXML file for the StockPage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StockPage.fxml"));
        BorderPane root = loader.load();

        // Create a scene with the root layout and apply the CSS stylesheet
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        // Set the title of the stage
        primaryStage.setTitle("Stock and Metals Price Checker by Gökdeniz Akbuga");

        // Set the unique application icon (use your image file path)
        Image appIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        primaryStage.getIcons().add(appIcon);

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to change scenes dynamically
    public void changeScene(Stage stage, String fxmlFile) throws Exception {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        BorderPane newRoot = loader.load();

        // Create a new scene for the new page and apply the CSS stylesheet
        Scene newScene = new Scene(newRoot);
        newScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        // Set the new scene on the primary stage
        stage.setScene(newScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
