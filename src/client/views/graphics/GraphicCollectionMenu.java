package client.views.graphics;

import client.controllers.AccountClient;
import client.controllers.CollectionClient;
import client.controllers.ShopClient;
import client.models.Shop;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.jfoenix.controls.*;
import client.controllers.ClientManager;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Response;
import models.cards.Deck;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.Spell;
import client.views.Graphics;
import java.io.File;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import static client.views.Graphics.Menu.MAIN_MENU;
import static client.views.Graphics.alert;
import static client.views.Graphics.playMusic;
import static client.views.graphics.ShopController.getCardPane;

public class GraphicCollectionMenu implements Initializable {


    public JFXTextField newDeckNameTxt;
    public JFXButton saveDeckBtn;
    public JFXButton deck;
    public VBox deckList;
    public VBox selectedDeckCardList;
    public AnchorPane selectedDeck;
    public JFXButton importDeckBtn;
    public JFXButton exportDeckBtn;
    public JFXTextField exportPathTxt;
    public JFXTextField importPathTxt;
    public AnchorPane root;
    private ToggleGroup selectedDeckToggleGroup = new ToggleGroup();
    private final Background ordinaryBackground = new Background(new BackgroundFill(Color.VIOLET,
            new CornerRadii(5), Insets.EMPTY));
    private final Background deleteBackground = new Background(new BackgroundFill(Color.RED,
            new CornerRadii(5), Insets.EMPTY));
    private final Background validBackground = new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(5), Insets.EMPTY));
    private final Background selectedDeckBackGround = new Background(new BackgroundFill(Color.AQUA, new CornerRadii(5), Insets.EMPTY));
    public JFXMasonryPane cardContainer;
    public ImageView backBtn;
    public TextField searchField;
    public Label filterNone, filterHeroes, filterMinions, filterSpells;
    public Type filterType = Card.class;
    private YaGsonBuilder deckJsonBuilder = new YaGsonBuilder().setPrettyPrinting();
    private YaGson deckJson = new YaGson();
    final File savedDecksPath = new File("src/data/saved-decks");


    private void changeAsWrong(JFXTextField textField, JFXButton button, boolean isWrong) {
        button.setDisable(isWrong);
        if (isWrong) {
            textField.setStyle("-jfx-focus-color: #FF0000; -jfx-unfocus-color: #FF0000;");
        } else {
            textField.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        }
    }

    public void createNewDeck() {
        playMusic("sfx_ui_select.m4a");
        String deckName = newDeckNameTxt.getText();
        if (deckName.equals("")) {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, true);
            return;
        }
        Response response = CollectionClient.createDeck(deckName);
        if (response.OK) {
            AnchorPane deckPane = getDeckPane(deckName);
            deckList.getChildren().add(deckPane);
            newDeckNameTxt.setText("");
        } else {
            alert("failed to create", "deck creation", response.message);
        }
    }

    public AnchorPane getDeckPane(String deckName) {
        AnchorPane deckPane = new AnchorPane();
        deckPane.setPrefSize(180, 50);
        deckPane.setBackground(ordinaryBackground);

        Label cardNameLbl = new Label(deckName);
        cardNameLbl.relocate(15, 12);
        cardNameLbl.setPrefWidth(200);
        cardNameLbl.setAlignment(Pos.CENTER);
        cardNameLbl.getStyleClass().add("card-name-label");
        deckPane.getChildren().add(cardNameLbl);

        JFXRadioButton radioButton = new JFXRadioButton("");
        radioButton.setToggleGroup(selectedDeckToggleGroup);
        radioButton.relocate(5, 17);
        radioButton.setOnMouseClicked(event -> {
            playMusic("sfx_ui_select.m4a");
            Response isValidRes = CollectionClient.isValid(deckName);
            if (isValidRes.OK) {
                Response response = CollectionClient.setMainDeck(deckName);
                if (!response.OK) {
                    alert("deck not selected", "set main deck", response.message);
                }

            } else {
                alert("invalid deck", "deck validation", isValidRes.message);
            }
        });
        radioButton.setVisible(false);
        deckPane.getChildren().add(radioButton);
        Response validationResponse = CollectionClient.isValid(deckName);
        boolean deckIsValid = false;
        if(validationResponse.OK) {
            deckIsValid = (boolean) validationResponse.data;
        }

        Background backgroundBtn = (deckIsValid ? validBackground : ordinaryBackground);
        deckPane.setBackground(backgroundBtn);
        String tmp = "";

        Response mainDeckResponse = CollectionClient.getMainDeck();
        if(mainDeckResponse.OK) {
            if(mainDeckResponse.data != null) { // User has a main deck
                tmp = ((Deck) mainDeckResponse.data).name;
            }
        }

        final String mainDeckName = tmp;
        radioButton.setSelected(mainDeckName.equals(deckName));
        radioButton.setVisible(deckIsValid);


        JFXButton deleteDeckBtn = new JFXButton("Delete");
        deleteDeckBtn.setVisible(false);
        deleteDeckBtn.setBackground(deleteBackground);
        deleteDeckBtn.setOnMouseClicked(event -> {
            exportDeckBtn.setDisable(true);
            playMusic("sfx_ui_select.m4a");
            Response response = CollectionClient.removeDeck(((Label) deckPane.getChildren().get(0)).getText());
            if (!response.OK) {
                alert("deck not removed", "invalid remove", response.message);
            }
            deckList.getChildren().remove(deckPane);
            if (selectedDeck == deckPane) {
                selectedDeck = null;
                selectedDeckCardList.getChildren().clear();
            }


        });
        deckPane.getChildren().add(deleteDeckBtn);
        deleteDeckBtn.resize(180, 50);
        AnchorPane.setTopAnchor(deleteDeckBtn, 12.0);
        AnchorPane.setRightAnchor(deleteDeckBtn, 5.0);


        deckPane.setOnMouseEntered(event -> deleteDeckBtn.setVisible(true));
        deckPane.setOnMouseExited(event -> deleteDeckBtn.setVisible(false));

        deckPane.setOnMouseClicked(event -> {
            boolean finalDeckIsValid = (boolean) CollectionClient.isValid(deckName).data;

            exportDeckBtn.setDisable(!finalDeckIsValid);
            playMusic("sfx_ui_select.m4a");
            cardContainer.getChildren().forEach(node -> node.setDisable(false));
            deckList.getChildren().forEach(node -> {
                AnchorPane nodePane = (AnchorPane) node;
                Response validResponse = CollectionClient.isValid(((Label) nodePane.getChildren().get(0)).getText());
                boolean isValid = false;
                if(validationResponse.OK) {
                    isValid = (boolean) validResponse.data;
                } else {
                    alert("Error", "Error", validationResponse.message);
                }
                Background background = (isValid ? validBackground : ordinaryBackground);
                nodePane.setBackground(background);
                JFXRadioButton rbtn = (JFXRadioButton) nodePane.getChildren().get(1);
                rbtn.setSelected(mainDeckName.equals(((Label) nodePane.getChildren().get(0)).getText()));
                rbtn.setVisible(isValid);
            });
            deckPane.setBackground(selectedDeckBackGround);
            selectedDeckCardList.getChildren().clear();
            ((Deck) CollectionClient.getDeck(deckName).data).cards.forEach((card, integer) ->
                    selectedDeckCardList.getChildren().add(getMiniCardPane(card.name, false, integer)));
            selectedDeck = deckPane;
        });

        return deckPane;
    }

    public AnchorPane getMiniCardPane(String cardName, boolean isInShop, int count) {

        AnchorPane cardPane = new AnchorPane();

        cardPane.setPrefSize(200, 50);
        cardPane.setBackground(new Background(new BackgroundFill(Color.ORANGE, new CornerRadii(5), Insets.EMPTY)));


        Label cardNameLbl = new Label(cardName.toUpperCase() + " x" + count);
        cardNameLbl.relocate(15, 17);
        cardNameLbl.setPrefWidth(200);
        cardNameLbl.setAlignment(Pos.CENTER);
        cardNameLbl.getStyleClass().add("card-name-label");
        cardPane.getChildren().add(cardNameLbl);


        JFXButton deleteBtn = new JFXButton("remove");
        deleteBtn.setPrefSize(220, 50);
        deleteBtn.setVisible(false);
        deleteBtn.setOnMouseDragEntered(event -> deleteBtn.setVisible(true));
        deleteBtn.setOnMouseDragExited(event -> deleteBtn.setVisible(false));
        deleteBtn.setBackground(deleteBackground);

        deleteBtn.setOnMouseClicked(event -> {
            exportDeckBtn.setDisable(true);
            playMusic("sfx_ui_select.m4a");
            final String selectedDeckName = ((Label) selectedDeck.getChildren().get(0)).getText();
            Response response = CollectionClient.removeCardFromDeck(selectedDeckName, cardName);
            if(response.OK) {
                Deck newDeck = (Deck) response.data;

                selectedDeckCardList.getChildren().clear();
                Map<Card, Integer> newDeckCards = newDeck.cards;
                newDeckCards.forEach((newCard, cardCount) -> selectedDeckCardList.getChildren().add(getMiniCardPane(newCard.name, false, cardCount)));

                deleteBtn.setVisible(false);

                if (!(boolean) CollectionClient.isValid(selectedDeckName).data) {
                    selectedDeck.setBackground(selectedDeckBackGround);
                    selectedDeck.getChildren().get(1).setVisible(false);
                    ((JFXRadioButton) (selectedDeck.getChildren().get(1))).setSelected(false);
                }
            } else {
                alert("Error", "Error", response.message);
            }
//            cardContainer.getChildren().forEach(node -> {
//                if (((Label) ((AnchorPane) node).getChildren().get(0)).getText().equals(cardName)) {
//                    node.setDisable(false);
//                }
//            });
        });
        cardPane.getChildren().add(deleteBtn);


        cardPane.setOnMouseEntered(event -> deleteBtn.setVisible(true));
        cardPane.setOnMouseExited(event -> deleteBtn.setVisible(false));

        return cardPane;
    }

    private void updateCards(String q, Type type) {
        cardContainer.getChildren().clear();
        Map<Card, Integer> cards;
        if (q == null) {
            q = "";
        }
        cards = ((Map<Card, Integer>) CollectionClient.search(q, type.getTypeName()).data);
        cards.forEach((card, integer) -> {
            if (card.getClass() == type || type == Card.class) {
                AnchorPane cardPane = getCardPane(card, true, integer);

                cardPane.setOnMouseEntered(event -> {
                    Graphics.playMusic("sfx_ui_select.m4a");
                    JFXButton sell = new JFXButton("SELL");
                    sell.setLayoutX(110);
                    sell.setLayoutY(210);
                    sell.setPrefSize(90, 62);
                    sell.getStyleClass().add("shop-buy-button");
                    JFXButton add = new JFXButton("ADD");
                    add.setLayoutX(25);
                    add.setLayoutY(210);
                    add.setPrefSize(90, 62);
                    add.getStyleClass().add("shop-cancel-button");
                    cardPane.getChildren().addAll(sell, add);
                    sell.setOnMouseClicked(bought -> {
                        Graphics.playMusic("sfx_ui_select.m4a");
                        Response buyResponse = ShopClient.sell(card.name);
                        if(buyResponse.OK)
                            updateCards(searchField.getText(), filterType);
                        else
                            alert("Error", "sell failed", buyResponse.message);
                    });
                    add.setOnMouseClicked(canceled -> {
                        Graphics.playMusic("sfx_ui_select.m4a");
                        if (selectedDeck == null) {
                            Graphics.alert("Error", "Can't add card", "please select a deck first.");
                            return;
                        }
                        String deckName = ((Label) selectedDeck.getChildren().get(0)).getText();
                        String cardName = card.name;
                        Response addResponse = CollectionClient.addCardToDeck(deckName, cardName);
                        if (addResponse.OK) {
                            selectedDeckCardList.getChildren().clear();
                            Map<Card, Integer> newDeckCards = ((Deck) addResponse.data).cards;
                            newDeckCards.forEach((newCard, count) -> selectedDeckCardList.getChildren().add(getMiniCardPane(newCard.name, false, count)));
                        } else {
                            alert("Error", "invalid addition", addResponse.message);
                        }
                        cardPane.getChildren().removeAll(sell, add);
                    });
                    cardPane.setOnMouseExited(event1 -> {
                        cardPane.getChildren().removeAll(sell, add);
                    });
                });


//                cardPane.setOnMouseClicked(event -> {
//                    playMusic("sfx_ui_select.m4a");
//                });
                cardContainer.getChildren().add(cardPane);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exportPathTxt.textProperty().addListener(observable -> exportDeckBtn.setDisable(false));
        importPathTxt.textProperty().addListener(observable -> importDeckBtn.setDisable(false));

        importPathTxt.setOnMouseClicked(event -> Graphics.playMusic("sfx_ui_select.m4a"));
        exportPathTxt.setOnMouseClicked(event -> Graphics.playMusic("sfx_ui_select.m4a"));

        exportDeckBtn.setDisable(true);

        newDeckNameTxt.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));

        newDeckNameTxt.textProperty().addListener(((observable, oldValue, newValue) -> {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, false);
        }));

        Response response = CollectionClient.getDecks();
        if(response.OK) {
            Map<String, Deck> decks = (HashMap<String, Deck>) response.data;
            decks.forEach((name, deck) -> {
                AnchorPane deckAnchorPane = getDeckPane(name);
                deckList.getChildren().add(deckAnchorPane);
            });
        } else {
            alert("Error", "can not get decks", response.message);
        }

        updateCards("", filterType);

        backBtn.setOnMouseClicked(event -> {
            playMusic("sfx_ui_select.m4a");
            Graphics.setMenu(MAIN_MENU);
        });

        searchField.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));

        searchField.textProperty().addListener(((observable, oldValue, newValue) -> updateCards(newValue, filterType)));

        Label[] filterLabels = new Label[]{filterNone, filterHeroes, filterMinions, filterSpells};
        for (Label filterLabel : filterLabels) {
            filterLabel.setOnMouseClicked(event -> {
                playMusic("sfx_ui_select.m4a");
                for (Label otherLabel : filterLabels)
                    otherLabel.getStyleClass().remove("selected");
                filterLabel.getStyleClass().add("selected");
                if (filterLabel == filterHeroes) filterType = Hero.class;
                else if (filterLabel == filterMinions) filterType = Minion.class;
                else if (filterLabel == filterSpells) filterType = Spell.class;
                else filterType = Card.class;
                updateCards(searchField.getText(), filterType);
            });
        }


        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.A) {
                for (Card card : Shop.getInstance().getCardsCollection().getCardsList()) {
                    ClientManager.incrementDrake(card.getPrice());
                    try {
                        ClientManager.buy(card.getName());
                    } catch (Collection.CollectionException | Account.NotEnoughDrakeException ignored) { }
                }
                updateCards("", Card.class);
            }
        });

    }


    private String getDirectory() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();


        directoryChooser.setInitialDirectory(savedDecksPath);
        directoryChooser.setTitle("Please select a Directory to save your deck");

        Stage fileChooserStage = new Stage();
        fileChooserStage.setScene(new Scene(new AnchorPane()));


        File file = directoryChooser.showDialog(fileChooserStage);

        return file.getPath();
    }

    private File getFile() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("deck", "*.deck"));


        fileChooser.setInitialDirectory(savedDecksPath);
        fileChooser.setTitle("Please select a valid .json File");

        File file = fileChooser.showOpenDialog(Graphics.stage);

        return file;
    }

    public void importDeck(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");

        File file = new File(importPathTxt.getText());
        if (!file.exists())
            file = getFile();
        if (file == null || !file.exists())
            return;
        importPathTxt.setText(file.getAbsolutePath());

        Deck deck = null;
        try {
            FileReader deckReader = new FileReader(file);
            deck = deckJson.fromJson(deckReader, Deck.class);
            deckReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportDeck(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");

        String deckName = ((Label) selectedDeck.getChildren().get(0)).getText();

        Deck deck = (Deck) CollectionClient.getDeck(deckName).data;

        File file = new File(exportPathTxt.getText());
        if (!file.exists())
            file = new File(getDirectory() + "/" + deckName + ".deck");
        try {
            file.createNewFile();
        } catch (IOException e) {
            return;
        }
        exportPathTxt.setText(file.getAbsolutePath());

        try {
            FileWriter deckWriter = new FileWriter(file);
            deckJson = deckJsonBuilder.create();
            deckWriter.write(deckJson.toJson(deck));
            deckWriter.flush();
            deckWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Graphics.alert("Congrats", "Exported", "Your Deck Exported Successfully.");
    }
}
