package views.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
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
import javafx.util.Callback;
import models.Account;
import models.Collection;
import models.Deck;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import sun.invoke.empty.Empty;
import sun.util.resources.cldr.bas.CalendarData_bas_CM;
import views.Graphics;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static views.graphics.ShopController.getCardPane;

public class GraphicCollectionMenu implements Initializable {


    public JFXTextField newDeckNameTxt;
    public JFXButton saveDeckBtn;
    public JFXButton deck;
    public VBox deckList;
    public VBox selectedDeckCardList;
    public AnchorPane selectedDeck;

    public JFXMasonryPane cardContainer;
    public ImageView backBtn;
    public TextField searchField;
    public Label filterNone, filterHeroes, filterMinions, filterSpells;
    public Type filterType = Card.class;


    public void deleteDeck() {
        try {
            ClientManager.deleteDeck(((JFXButton) deckList.getChildren().get(0)).getText());
        } catch (Account.DeckNotFoundException ignored) { }
    }

    public void removeCardFromDeck() {
        String cardName = ((JFXButton)selectedDeckCardList.getChildren().get(0)).getText().replaceAll(" ×//d", "");
        try {
            ClientManager.removeCardFromDeck(cardName, ((Label)selectedDeck.getChildren().get(0)).getText());
//            updateListView(selectedDeckCardList, ClientManager.getDeck(selectedDeck.getText()).getCards());
        } catch (Collection.CardNotFoundException | Account.DeckNotFoundException ignored) { }

    }

    private void changeAsWrong(JFXTextField textField, JFXButton button, boolean isWrong) {
        button.setDisable(isWrong);
        if (isWrong) {
            textField.setStyle("-jfx-focus-color: #FF0000; -jfx-unfocus-color: #FF0000;");
        } else {
            textField.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        }
    }

    public void createNewDeck() {
        String deckName = newDeckNameTxt.getText();
        if (deckName.equals("")) {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, true);
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

        deckPane.setBackground(new Background(new BackgroundFill(Color.VIOLET, new CornerRadii(5), Insets.EMPTY)));

        Label cardNameLbl = new Label(deckName);
        cardNameLbl.relocate(15, 25);
        cardNameLbl.setPrefWidth(200);
        cardNameLbl.setAlignment(Pos.CENTER);
        cardNameLbl.getStyleClass().add("card-name-label");
        deckPane.getChildren().add(cardNameLbl);

        JFXButton deleteDeckBtn = new JFXButton("Delete");
        deleteDeckBtn.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(5), Insets.EMPTY)));
        deleteDeckBtn.setOnMouseClicked(event -> {
            try {
                ClientManager.deleteDeck(((Label)deckPane.getChildren().get(0)).getText());
                deckList.getChildren().remove(deckPane);
                if (selectedDeck == deckPane) {
                    selectedDeck = null;
                    selectedDeckCardList.getChildren().clear();
                }
            } catch (Account.DeckNotFoundException ignored) { }
        });
        deckPane.getChildren().add(deleteDeckBtn);

        deckPane.setOnMouseClicked(event -> {
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
        cardNameLbl.relocate(15, 10 );
        cardNameLbl.setPrefWidth(200);
        cardNameLbl.setAlignment(Pos.CENTER);
        cardNameLbl.getStyleClass().add("card-name-label");
        cardPane.getChildren().add(cardNameLbl);

        JFXButton deleteBtn = new JFXButton("remove");
        deleteBtn.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(5), Insets.EMPTY)));
        deleteBtn.setOnMouseClicked(event -> {
            try {
                ClientManager.removeCardFromDeck(cardName, ((Label)selectedDeck.getChildren().get(0)).getText());
                selectedDeckCardList.getChildren().remove(cardPane);
            } catch (Collection.CardNotFoundException | Account.DeckNotFoundException ignored) { }
        });
        cardPane.getChildren().add(deleteBtn);

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
                    if (selectedDeck == null) {
                        Graphics.alert("Error", "Can't add card", "please select a deck first.");
                        return;
                    }
                    try {
                        String cardName = ((Label)cardPane.getChildren().get(0)).getText();
                        String deckName = ((Label)selectedDeck.getChildren().get(0)).getText();
                        ClientManager.addCardToDeck(cardName, deckName);
                        selectedDeckCardList.getChildren().add(getMiniCardPane(cardName, false));
                        if (ClientManager.isValid(deckName))
                            selectedDeck.setBackground(new Background(new BackgroundFill(Color.GREEN,
                                    CornerRadii.EMPTY, Insets.EMPTY)));
                    } catch (Deck.HeroExistsInDeckException e) {
                        Graphics.alert("Error", "Can't add hero to deck", "You can have exacly one hero in any deck.");
                    } catch (Deck.HeroNotExistsInDeckException e) {
                        Graphics.alert("Error", "Can't add hero to deck", "You should have at least one hero in your deck.");
                    } catch (Deck.DeckFullException e) {
                        Graphics.alert("Error", "Can't add card to deck", "your deck is full.");
                    }catch (Account.DeckNotFoundException | Collection.CardNotFoundException ignored) {}

                });
                cardContainer.getChildren().add(cardPane);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        newDeckNameTxt.textProperty().addListener(((observable, oldValue, newValue) -> {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, false);
        }));


        updateCards("", filterType);

        backBtn.setOnMouseClicked(event -> Graphics.stage.getScene().setRoot(Graphics.mainMenuRoot));

        searchField.textProperty().addListener(((observable, oldValue, newValue) -> updateCards(newValue, filterType)));

        Label[] filterLabels = new Label[]{filterNone, filterHeroes, filterMinions, filterSpells};
        for (Label filterLabel : filterLabels) {
            filterLabel.setOnMouseClicked(event -> {
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
