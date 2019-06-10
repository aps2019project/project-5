package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Graphics extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        // Fog fog = new Fog(400, 400, Color.rgb(0, 0, 0));
        Parent root = FXMLLoader.load(Graphics.class.getResource("../layouts/account_menu.fxml"));
        Scene scene = new Scene(root, 1920, 1080);
        stage.setFullScreen(true);
        setCursor(scene);
        stage.setScene(scene);
        stage.show();
    }

    private static void setCursor(Scene scene) {
        Image image = new Image("resources/cursor.png"); //pass in the image path
        scene.setCursor(new ImageCursor(image));
    }

    public static void showMainMenu() throws IOException {
        Parent root = FXMLLoader.load(Graphics.class.getResource("../layouts/main_menu.fxml"));
        Scene scene = new Scene(root, 1920, 1080);
        setCursor(scene);
        stage.setScene(scene);
    }
}
