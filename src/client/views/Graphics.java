package client.views;

import client.controllers.AccountClient;
import client.controllers.ClientManager;
import client.models.Shop;
import client.views.graphics.GraphicBattleController;
import client.views.graphics.GraphicPreBattleMenu;
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
import client.models.Account;
import client.models.Collection;
import client.models.Deck;

import javax.security.auth.login.AccountNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static client.views.Graphics.Menu.MAIN_MENU;

public class Graphics extends Application {
    public static Stage stage;

    public static Parent accountMenuRoot, mainMenuRoot, multiSingleRoot, customSelectRoot,
            profileRoot, watchRoot, codexRoot, customCardRoot, battleMenuRoot, collectionMenuRoot, shopMenuRoot;


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
        COLLECTION_MENU("/client/layouts/collection_menu.fxml"),
        SHOP_MENU("/client/layouts/shop.fxml"),
        ACCOUNT_MENU("/client/layouts/account_menu.fxml"),
        MATCH_SELECT_MENU("/client/layouts/match_select.fxml"),
        MAIN_MENU("/client/layouts/main_menu.fxml"),
        MULTI_SINGLE("/client/layouts/multi_single.fxml"),
        CUSTOM_SELECT("/client/layouts/custom_select.fxml"),
        BATTLE("/client/layouts/battle.fxml"),
        CUSTOM_CARD("/client/layouts/custom_card.fxml"),
        WAITING_MENU("/client/layouts/waiting.fxml");
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

    public void loadFXML() {
        try {
            GridPane tmpGridPane = new GridPane();
            tmpGridPane.setOnMouseClicked(event -> setMenu(MAIN_MENU));
            profileRoot = tmpGridPane;
            watchRoot = tmpGridPane;
            codexRoot = tmpGridPane;
            profileRoot = tmpGridPane;
            watchRoot = tmpGridPane;
            codexRoot = tmpGridPane;
            accountMenuRoot = FXMLLoader.load(Graphics.class.getResource("/client/layouts/account_menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        loadFXML();
        testLogin();
        stage = primaryStage;
        Scene scene = new Scene(accountMenuRoot, 1920, 1080);
        scene.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));
        Image image = new Image("client/resources/images/cursor.png");
        scene.setCursor(new ImageCursor(image));
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
        setMenu(MAIN_MENU);
    }

    private void testLogin() {
        Scanner scn = new Scanner(System.in);
        int user = scn.nextInt();
        GraphicPreBattleMenu.matchMode = 1;
        GraphicPreBattleMenu.isMultiPlayer = true;
        GraphicPreBattleMenu.isStoryMode = false;
        switch (user) {
            case 1:
                AccountClient.login("mahdi", "mahdi");
                break;
            case 2:
                AccountClient.login("ali", "ali");
                break;
            case 3:
                AccountClient.login("amin", "amin");
                break;
        }
//        GraphicPreBattleMenu.battleRequest();
    }

    public static MediaPlayer playMusic(String musicName) {
        Media sound = new Media(new File("src/client/resources/sounds/" + musicName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        return mediaPlayer;
    }

}
