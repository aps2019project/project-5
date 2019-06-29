package client.views;

import client.controllers.ClientManager;
import client.models.Shop;
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

import java.io.File;
import java.io.IOException;

import static client.views.Graphics.Menu.MAIN_MENU;

public class Graphics extends Application {
    public static Stage stage;

    public static Parent accountMenuRoot, mainMenuRoot, multiSingleRoot, customSelectRoot,
            matchSelectRoot, profileRoot, watchRoot, codexRoot, customCardRoot, battleMenuRoot, collectionMenuRoot, shopMenuRoot;


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
        ACCOUNT_MENU(accountMenuRoot),
        MATCH_SELECT_MENU(matchSelectRoot),
        MAIN_MENU(mainMenuRoot),
        MULTI_SINGLE(multiSingleRoot),
        CUSTOM_SELECT(customSelectRoot),
        BATTLE("/client/layouts/battle.fxml"),
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
            matchSelectRoot = FXMLLoader.load(Graphics.class.getResource("/client/layouts/match_select.fxml"));
            mainMenuRoot = FXMLLoader.load(Graphics.class.getResource("/client/layouts/main_menu.fxml"));
            accountMenuRoot = FXMLLoader.load(Graphics.class.getResource("/client/layouts/account_menu.fxml"));
            customCardRoot = FXMLLoader.load(Graphics.class.getResource("/client/layouts/custom_card.fxml"));
            multiSingleRoot = FXMLLoader.load(Graphics.class.getResource("/client/layouts/multi_single.fxml"));
            customSelectRoot = FXMLLoader.load(Graphics.class.getResource("/client/layouts/custom_select.fxml"));
//            battleMenuRoot = FXMLLoader.load(Graphics.class.getResource("../client.layouts/battle.fxml"));
//            collectionMenuRoot = FXMLLoader.load(Graphics.class.getResource("../client.layouts/collection_menu.fxml"));
//            shopMenuRoot = FXMLLoader.load(Graphics.class.getResource("../client.layouts/shop.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1000);
        }
    }

    @Override
    public void start(Stage primaryStage) {

//        try {
//            ClientManager.login("mahdi", "mahdi");
//            ClientManager.setOpponent("AI", true);
//            ClientManager.setGameMode(ClientManager.GameMode.DEATH_MATCH);
//        } catch (Account.InvalidPasswordException | Account.InvalidUsernameException  ignored) {}

        Account.loadAccounts();
        System.out.println(Shop.getInstance().getCardsCollection().getMinions().size());
//        System.out.println(Account.getAccounts().size());
        loadFXML();
        stage = primaryStage;
        Scene scene = new Scene(accountMenuRoot, 1920, 1080);
        scene.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));
        Image image = new Image("client/resources/images/cursor.png");
        scene.setCursor(new ImageCursor(image));
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public static MediaPlayer playMusic(String musicName) {
        Media sound = new Media(new File("src/client/resources/sounds/" + musicName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        return mediaPlayer;
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
            ClientManager.createDeck("aliDeck");


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
            ClientManager.createDeck("mahdiDeck");
            ClientManager.createDeck("mahdiDeck2");
            ClientManager.createDeck("mahdiDeck3");
            ClientManager.addCardToDeck("empower", "mahdiDeck");
            ClientManager.addCardToDeck("fire dragon", "mahdiDeck");
            ClientManager.addCardToDeck("fire dragon", "mahdiDeck");
            ClientManager.addCardToDeck("Hog Head Demon", "mahdiDeck");
            ClientManager.addCardToDeck("Persian Swordsman", "mahdiDeck");
            ClientManager.addCardToDeck("rostam", "mahdiDeck");
            ClientManager.addCardToDeck("Persian Horse Rider", "mahdiDeck");
            ClientManager.addCardToDeck("Persian Horse Rider", "mahdiDeck");
            ClientManager.addCardToDeck("Persian Horse Rider", "mahdiDeck");
            ClientManager.addCardToDeck("Persian Horse Rider", "mahdiDeck");
            ClientManager.addCardToDeck("Persian Champion", "mahdiDeck");
            ClientManager.addCardToDeck("Persian Champion", "mahdiDeck");
            ClientManager.addCardToDeck("Turan Archer", "mahdiDeck");
            ClientManager.addCardToDeck("Turan Archer", "mahdiDeck");
            ClientManager.addCardToDeck("Turan Wand", "mahdiDeck");
            ClientManager.addCardToDeck("Turan Wand", "mahdiDeck");
            ClientManager.addCardToDeck("persian horse rider", "mahdiDeck");
            ClientManager.addCardToDeck("persian horse rider", "mahdiDeck");
            ClientManager.addCardToDeck("Hog Head Demon", "mahdiDeck");
            ClientManager.addCardToDeck("Hog Head Demon", "mahdiDeck");

            ClientManager.addCardToDeck("empower", "mahdiDeck2");
            ClientManager.addCardToDeck("fire dragon", "mahdiDeck2");
            ClientManager.addCardToDeck("fire dragon", "mahdiDeck2");
            ClientManager.addCardToDeck("Hog Head Demon", "mahdiDeck2");
            ClientManager.addCardToDeck("Persian Swordsman", "mahdiDeck2");
            ClientManager.addCardToDeck("rostam", "mahdiDeck2");
            ClientManager.addCardToDeck("Persian Horse Rider", "mahdiDeck2");
            ClientManager.addCardToDeck("Persian Horse Rider", "mahdiDeck2");
            ClientManager.addCardToDeck("Persian Horse Rider", "mahdiDeck2");
            ClientManager.addCardToDeck("Persian Horse Rider", "mahdiDeck2");
            ClientManager.addCardToDeck("Hog Head Demon", "mahdiDeck2");
            ClientManager.addCardToDeck("Persian Champion", "mahdiDeck2");
            ClientManager.addCardToDeck("Turan Archer", "mahdiDeck2");
            ClientManager.addCardToDeck("Turan Archer", "mahdiDeck2");
            ClientManager.addCardToDeck("Turan Wand", "mahdiDeck2");
            ClientManager.addCardToDeck("Turan Wand", "mahdiDeck2");
            ClientManager.addCardToDeck("persian horse rider", "mahdiDeck2");
            ClientManager.addCardToDeck("persian horse rider", "mahdiDeck2");
            ClientManager.addCardToDeck("Persian Horse Rider", "mahdiDeck2");
            ClientManager.addCardToDeck("Hog Head Demon", "mahdiDeck2");

            ClientManager.selectDeck("mahdiDeck");


        } catch (Account.UsernameExistsException | Account.InvalidPasswordException | Account.InvalidUsernameException | Collection.CollectionException | Account.NotEnoughDrakeException | Account.NotLoggedInException | Account.DeckExistsException | Account.DeckNotFoundException | Deck.HeroExistsInDeckException | Deck.HeroNotExistsInDeckException | Deck.DeckFullException ignored) {
//            ignored.printStackTrace();
        }

    }
}