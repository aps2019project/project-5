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

    public static Parent accountMenuRoot, mainMenuRoot;

    static {
        try {
            accountMenuRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/account_menu.fxml"));
            mainMenuRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/main_menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Scene scene = new Scene(accountMenuRoot, 1920, 1080);
        Image image = new Image("resources/ui/cursor.png"); //pass in the image path
        scene.setCursor(new ImageCursor(image));
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }
}
