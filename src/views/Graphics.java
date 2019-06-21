package views;

import controllers.ClientManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import models.Account;
import models.Collection;
import models.Deck;

import java.io.File;
import java.io.IOException;

import static views.Graphics.Menu.CUSTOM_CARD;

public class Graphics extends Application {
    public static Stage stage;

    public static Parent accountMenuRoot, mainMenuRoot, multiSingleRoot, customSelectRoot,
            matchSelectRoot, profileRoot, watchRoot, codexRoot, customCardRoot;

    static {
        try {
            profileRoot = new GridPane();
            watchRoot = new GridPane();
            codexRoot = new GridPane();
            matchSelectRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/match_select.fxml"));
            mainMenuRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/main_menu.fxml"));
            accountMenuRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/account_menu.fxml"));
            customCardRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/custom_card.fxml"));
            multiSingleRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/multi_single.fxml"));
            customSelectRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/custom_select.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    public static void alert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public static void setMenu(Menu menu) {
        if (!menu.isPreLoaded) {
            try {
                Graphics.stage.getScene().setRoot(
                        FXMLLoader.load(Graphics.class.getResource(menu.getFile()))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Graphics.stage.getScene().setRoot(menu.getRoot());
        }
    }

    public enum Menu {
        COLLECTION_MENU("../layouts/collection_menu.fxml"),
        SHOP_MENU("../layouts/shop.fxml"),
        ACCOUNT_MENU(accountMenuRoot),
        MATCH_SELECT_MENU(matchSelectRoot),
        MAIN_MENU(mainMenuRoot),
        MULTI_SINGLE(multiSingleRoot),
        CUSTOM_SELECT(customSelectRoot),
        BATTLE("../layouts/battle.fxml"),
        CUSTOM_CARD(customCardRoot);
        Parent root;
        String file;
        boolean isPreLoaded;

        Menu(Parent root) {
            this.root = root;
            isPreLoaded = true;
        }

        Menu(String file) {
            this.file = file;
            isPreLoaded = false;
        }

        public String getFile() {
            return this.file;
        }

        public boolean isPreLoaded() {
            return isPreLoaded;
        }

        public Parent getRoot() {
            return this.root;
        }
    }

    public static void createNewStage(Stage stage) {

    }

    @Override
    public void start(Stage primaryStage) {


        /// For test:

        stage = primaryStage;

        Scene scene = new Scene(accountMenuRoot, 1920, 1080);
        scene.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));
        Image image = new Image("resources/images/cursor.png");
        scene.setCursor(new ImageCursor(image));
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public static MediaPlayer playMusic(String musicPath) {
//      new AudioClip(new File("src/resources/sounds/" + musicPath).toURI().toString()).play();
        Media sound = new Media(new File("src/resources/sounds/" + musicPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        return mediaPlayer;
    }

}
