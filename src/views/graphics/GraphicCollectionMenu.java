package views.graphics;

import com.dd.plist.PropertyListFormatException;
import com.jfoenix.controls.*;
import controllers.ClientManager;
import controllers.Manager;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Account;
import models.Action;
import models.Collection;
import models.Deck;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import org.xml.sax.SAXException;
import sun.invoke.empty.Empty;
import sun.util.resources.cldr.bas.CalendarData_bas_CM;
import views.Graphics;
import views.SpriteMaker;
import views.menus.MainMenu;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static views.Graphics.Menu.MAIN_MENU;
import static views.Graphics.playMusic;
import static views.graphics.ShopController.getCardPane;

public class GraphicCollectionMenu implements Initializable {


    public JFXTextField newDeckNameTxt;
    public JFXButton saveDeckBtn;
    public JFXButton deck;
    public VBox deckList;
    public VBox selectedDeckCardList;
    public AnchorPane selectedDeck;
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


    private void changeAsWrong(JFXTextField textField, JFXButton button, boolean isWrong) {
        button.setDisable(isWrong);
        if (isWrong) {
            textField.setStyle("-jfx-focus-color: #FF0000; -jfx-unfocus-color: #FF0000;");
        } else {
            textField.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        }
    }

    public void createNewDeck() {
        Graphics.playMusic("sfx_ui_select.m4a");
        String deckName = newDeckNameTxt.getText();
        if (deckName.equals("")) {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, true);
            return;
        }
        try {
            ClientManager.createDeck(deckName);
            AnchorPane deckPane = getDeckPane(deckName);
            deckList.getChildren().add(deckPane);
        } catch (Account.DeckExistsException e) {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, true);
        } catch (Account.NotLoggedInException ignored) {}
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
            Graphics.playMusic("sfx_ui_select.m4a");
            try {
                if (ClientManager.isValid(deckName))
                    ClientManager.selectDeck(deckName);
            } catch (Account.DeckNotFoundException ignored) { }
        });
        radioButton.setVisible(false);
        deckPane.getChildren().add(radioButton);

        JFXButton deleteDeckBtn = new JFXButton("Delete");
        deleteDeckBtn.setVisible(false);
        deleteDeckBtn.setBackground(deleteBackground);
        deleteDeckBtn.setOnMouseClicked(event -> {
            Graphics.playMusic("sfx_ui_select.m4a");
            try {
                if (radioButton.isSelected())
                    ClientManager.selectDeck(null);
                ClientManager.deleteDeck(((Label)deckPane.getChildren().get(0)).getText());
                deckList.getChildren().remove(deckPane);
                if (selectedDeck == deckPane) {
                    selectedDeck = null;
                    selectedDeckCardList.getChildren().clear();
                }
            } catch (Account.DeckNotFoundException ignored) { }
        });
        deckPane.getChildren().add(deleteDeckBtn);
        deleteDeckBtn.resize(180, 50);
        AnchorPane.setTopAnchor(deleteDeckBtn, 12.0);
        AnchorPane.setRightAnchor(deleteDeckBtn, 5.0);


        deckPane.setOnMouseEntered(event -> deleteDeckBtn.setVisible(true));
        deckPane.setOnMouseExited(event -> deleteDeckBtn.setVisible(false));

        deckPane.setOnMouseClicked(event -> {
            Graphics.playMusic("sfx_ui_select.m4a");
            cardContainer.getChildren().forEach(node -> node.setDisable(false));
            deckList.getChildren().forEach(node -> {
                AnchorPane nodePane = (AnchorPane)node;
                try {
                    Background background = (ClientManager.isValid(((Label)nodePane.getChildren().get(0)).getText())) ? validBackground : ordinaryBackground;
                    nodePane.setBackground(background);
                } catch (Account.DeckNotFoundException ignored) { }
            });
            deckPane.setBackground(selectedDeckBackGround);
            selectedDeckCardList.getChildren().clear();
            try {
                ClientManager.getDeck(deckName).getCards().forEach(card ->
                        selectedDeckCardList.getChildren().add(getMiniCardPane(card.getName(), false)));
            } catch (Account.DeckNotFoundException ignored) { }
            selectedDeck = deckPane;
        });

        return deckPane;
    }


    public AnchorPane getMiniCardPane(String cardName, boolean isInShop) {

        AnchorPane cardPane = new AnchorPane();

        cardPane.setPrefSize(200, 50);
        cardPane.setBackground(new Background(new BackgroundFill(Color.ORANGE, new CornerRadii(5), Insets.EMPTY)));


        Label cardNameLbl = new Label(cardName.toUpperCase());
        cardNameLbl.relocate(15, 17 );
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
            Graphics.playMusic("sfx_ui_select.m4a");
            try {
                final String selectedDeckName = ((Label) selectedDeck.getChildren().get(0)).getText();
                ClientManager.removeCardFromDeck(cardName, selectedDeckName);
                selectedDeckCardList.getChildren().remove(cardPane);
                cardContainer.getChildren().forEach(node -> {
                    if (((Label) ((AnchorPane) node).getChildren().get(0)).getText().equals(cardName)) {
                        node.setDisable(false);
                    }
                });
                if (!ClientManager.isValid(selectedDeckName)) {
                    selectedDeck.setBackground(selectedDeckBackGround);
                    selectedDeck.getChildren().get(1).setVisible(false);
                    ((JFXRadioButton)(selectedDeck.getChildren().get(1))).setSelected(false);
                }
            } catch (Collection.CardNotFoundException | Account.DeckNotFoundException ignored) { }
        });
        cardPane.getChildren().add(deleteBtn);


        cardPane.setOnMouseEntered(event -> deleteBtn.setVisible(true));
        cardPane.setOnMouseExited(event -> deleteBtn.setVisible(false));

        return cardPane;
    }

    private void updateCards(String q, Type type) {
        cardContainer.getChildren().clear();
        List<Card> cards = new ArrayList<>();
        if (q == null || q.equals("")) {
            cards = ClientManager.getMyCollection().getCardsList();
        } else {
            try {
                cards = ClientManager.searchMyCard(q);
            } catch (Collection.CardNotFoundException ignored) {}
        }

        cards.forEach(card -> {
            if (card.getClass() == type || type == Card.class) {
                AnchorPane cardPane = getCardPane(card, true);
                cardPane.setOnMouseClicked(event -> {
                    Graphics.playMusic("sfx_ui_select.m4a");
                    if (selectedDeck == null) {
                        Graphics.alert("Error", "Can't add card", "please select a deck first.");
                        return;
                    }
                    try {
                        String cardName = ((Label)cardPane.getChildren().get(0)).getText();
                        String deckName = ((Label)selectedDeck.getChildren().get(0)).getText();
                        ClientManager.addCardToDeck(cardName, deckName);
                        selectedDeckCardList.getChildren().add(getMiniCardPane(cardName, false));
                        if (ClientManager.isValid(deckName)) {
                            selectedDeck.setBackground(validBackground);
                            selectedDeck.getChildren().get(1).setVisible(true);
                        }
                    } catch (Deck.DeckFullException e) {
                        Graphics.alert("Error", "Can't add card to deck", "your deck is full.");
                    }catch (Deck.HeroExistsInDeckException e) {
                        Graphics.alert("Error", "Can't add hero to deck", "You can have exacly one hero in any deck.");
                    } catch (Deck.HeroNotExistsInDeckException e) {
                        Graphics.alert("Error", "Can't add hero to deck", "You should have at least one hero in your deck.");
                    } catch (Collection.CardNotFoundException ignored) {
                        cardPane.setDisable(true);
                    } catch (Account.DeckNotFoundException ignored) { }

                });
                cardContainer.getChildren().add(cardPane);
            }

        });



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        newDeckNameTxt.setOnMouseClicked(event -> Graphics.playMusic("sfx_ui_select.m4a"));

        newDeckNameTxt.textProperty().addListener(((observable, oldValue, newValue) -> {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, false);
        }));

        try {
            ClientManager.getDecks().forEach(deck -> deckList.getChildren().add(getDeckPane(deck.getName())));
        } catch (Account.NotLoggedInException ignored) { }

        updateCards("", filterType);

        backBtn.setOnMouseClicked(event -> {
            Graphics.playMusic("sfx_ui_select.m4a");
            Graphics.setMenu(MAIN_MENU);
        });

        searchField.setOnMouseClicked(event -> Graphics.playMusic("sfx_ui_select.m4a"));

        searchField.textProperty().addListener(((observable, oldValue, newValue) -> updateCards(newValue, filterType)));

        Label[] filterLabels = new Label[]{filterNone, filterHeroes, filterMinions, filterSpells};
        for (Label filterLabel : filterLabels) {
            filterLabel.setOnMouseClicked(event -> {
                Graphics.playMusic("sfx_ui_select.m4a");
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
    }


}
