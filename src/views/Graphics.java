package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import resources.Fog;

import java.io.IOException;

public class Graphics extends Application {
    public static Stage stage;

    public static Parent shopMenuRoot, accountMenuRoot, mainMenuRoot, matchSelectRoot, play, profileRoot;

    static {
        try {
            //TODO : make true roots;
            profileRoot = new GridPane();
            matchSelectRoot = new GridPane();
            play = new GridPane();
            shopMenuRoot = new GridPane();
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
//        Fog fog = new Fog(500, 500, new Color(1, 0, 0, 1));
//        AnchorPane a = new AnchorPane(fog.getView());
        Scene scene = new Scene(mainMenuRoot, 1920, 1080);
        Image image = new Image("resources/ui/cursor.png");
        scene.setCursor(new ImageCursor(image));
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }
}
