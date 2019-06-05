import com.jfoenix.controls.JFXDialog;
import com.sun.javafx.cursor.ImageCursorFrame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.text.AbstractDocument;
import java.io.IOException;

public class Graphics extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Parent root = FXMLLoader.load(Graphics.class.getResource("layouts/account_menu.fxml"));
        Scene scene = new Scene(root, 1920, 1080);
        scene.getStylesheets().add("resources/stylesheets/style.css");
        stage.setFullScreen(true);
        setCursor(scene);
        stage.setScene(scene);
        stage.show();
    }

    public void setCursor(Scene scene) {
        Image image = new Image("resources/cursor.png"); //pass in the image path
        scene.setCursor(new ImageCursor(image));
    }
}
