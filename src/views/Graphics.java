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

import java.io.File;
import java.io.IOException;

public class Graphics extends Application {
    public static Stage stage;

    public static Parent shopMenuRoot, accountMenuRoot, mainMenuRoot,
            matchSelectRoot, profileRoot, watchRoot, collectionMenuRoot, codexRoot, deathMatchRoot,
            singleFlagRoot, multiFlagRoot, matchRoot;

    static {
        try {
            //TODO : make true roots;
            createTestUser();
            profileRoot = new GridPane();
            watchRoot = new GridPane();
            codexRoot = new GridPane();
            multiFlagRoot = new GridPane();
            singleFlagRoot = new GridPane();
            deathMatchRoot = new GridPane();
            matchSelectRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/match_select.fxml"));
            collectionMenuRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/collection_menu.fxml"));
            shopMenuRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/shop.fxml"));
            accountMenuRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/account_menu.fxml"));
            mainMenuRoot = FXMLLoader.load(Graphics.class.getResource("../layouts/main_menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
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
        try {
            Graphics.stage.getScene().setRoot(FXMLLoader.load(Graphics.class.getResource(menu.menuPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum Menu {
        COLLECTION_MENU("../layouts/collection_menu.fxml"),
        SHOP_MENU("../layouts/shop.fxml"),
        ACCOUNT_MENU("../layouts/account_menu.fxml"),
        MATCH_SELECT_MENU("../layouts/match_select.fxml"),
        MAIN_MENU("../layouts/main_menu.fxml"),
        DECK_SELECTION_MENU("../layouts/deck_select.fxml"),
        MATCH("../layouts/match.fxml"),
        MULTI_SINGLE("../layouts/multi_single.fxml"),
        CUSTOM_SELECT("../layouts/custom_select.fxml");
        String menuPath;

        Menu(String menuPath) {
            this.menuPath = menuPath;
        }
    }

    @Override
    public void start(Stage primaryStage) {

        playMusic("Afsar.mp3");
        /// For test:
        createTestUser();

        loadLayouts();
        stage = primaryStage;

        Scene scene = new Scene(accountMenuRoot, 1920, 1080);
        scene.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));
        Image image = new Image("resources/images/cursor.png");
        scene.setCursor(new ImageCursor(image));
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void playMusic(String musicFile) {
        Media sound = new Media(new File("src/resources/sounds/" +  musicFile ).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    private void loadLayouts() {
    }

    private static void createTestUser() {
        try {
            ClientManager.createAccount("ali", "ali");
            ClientManager.createAccount("mahdi", "mahdi");
            ClientManager.createAccount("amin", "amin");

            ClientManager.login("ali", "ali");
            ClientManager.buy("empower");
            ClientManager.buy("fire dragon");
            ClientManager.buy("fire dragon");
            ClientManager.buy("Hog Head Demon");
            ClientManager.buy("Persian Swordsman");
            ClientManager.buy("rostam");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Champion");
            ClientManager.buy("Persian Champion");
            ClientManager.buy("Turan Archer");
            ClientManager.buy("Turan Archer");
            ClientManager.buy("Turan Wand");
            ClientManager.buy("Turan Wand");
            ClientManager.buy("persian horse rider");
            ClientManager.buy("persian horse rider");
            ClientManager.buy("Hog Head Demon");
            ClientManager.buy("Hog Head Demon");
            ClientManager.buy("Hog Head Demon");


            ClientManager.login("mahdi", "mahdi");
            ClientManager.buy("empower");
            ClientManager.buy("fire dragon");
            ClientManager.buy("fire dragon");
            ClientManager.buy("Hog Head Demon");
            ClientManager.buy("Persian Swordsman");
            ClientManager.buy("rostam");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Horse Rider");
            ClientManager.buy("Persian Champion");
            ClientManager.buy("Persian Champion");
            ClientManager.buy("Turan Archer");
            ClientManager.buy("Turan Archer");
            ClientManager.buy("Turan Wand");
            ClientManager.buy("Turan Wand");
            ClientManager.buy("persian horse rider");
            ClientManager.buy("persian horse rider");
            ClientManager.buy("Hog Head Demon");
            ClientManager.buy("Hog Head Demon");
            ClientManager.buy("Hog Head Demon");


            try {
                ClientManager.login("ali", "ali");
            } catch (Account.InvalidUsernameException | Account.InvalidPasswordException ignored) {}


        } catch (Account.UsernameExistsException | Account.InvalidPasswordException | Account.InvalidUsernameException | Collection.CollectionException | Account.NotEnoughDrakeException ignored) { }

    }
}
