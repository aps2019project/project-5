package views.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import controllers.ClientManager;
import controllers.logic.Manager;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import models.Account;
import models.Collection;
import models.Deck;
import models.cards.Card;
import views.Error;
import views.Graphics;
import views.Log;
import views.Output;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

public class GraphicCollectionMenu implements Initializable {


    public JFXTextField searchingCardNameTxt;
    public JFXButton searchCardBtn;
    public JFXTextField newDeckNameTxt;
    public JFXButton saveDeckBtn;
    public JFXButton card1;
    public JFXButton deck;
    public JFXListView deckList;
    public JFXListView selectedDeckCardList;
    public JFXListView cardList;
    String selectedDeckName;

    public static void deleteDeck(Matcher matcher) {
        String deckName = matcher.group("name");
        try {
            Manager.deleteDeck(deckName);
            Output.log(Log.DECK_DELETED);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }
    }

    public static void removeCardFromDeck(Matcher matcher) {
        String cardName = matcher.group("card");
        String deckName = matcher.group("deck");
        try {
            Manager.removeCardFromDeck(cardName, deckName);
            Output.log(Log.CARD_REMOVED_FROM_COLLECTION);
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND_IN_COLLECTION);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }

    }

    public static void validateDeck(Matcher matcher) {
        String deckName = matcher.group("deck");
        try {
            if (Manager.isValid(deckName))
                Output.log(Log.DECK_IS_COMPLETED);
            else Output.err(Error.DECK_IS_NOT_COMPLETE);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }
    }

    public static void selectDeck(Matcher matcher) {
        String deckName = matcher.group("deck");
        try {
            Manager.selectDeck(deckName);
            Output.log(Log.DECK_SELECTED);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }
    }

    public static void showAllDecks(Matcher matcher) {
        try {
            List<Deck> decks = Manager.getDecks();
            for (Deck deck : decks)
                Output.print(deck);
        } catch (Account.NotLoggedInException e) {
            Output.err(Error.NOT_LOGGED_IN);
        }
    }

    public static void showDeck(Matcher matcher) {
        String deckName = matcher.group("deck");
        try {
            Deck deck = Manager.getAccount().getDeck(deckName);
            Output.log(deck.toString());
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }

    }

    private void changeAsWrong(JFXTextField textField, JFXButton button, boolean isWrong) {
        button.setDisable(isWrong);
        if (isWrong) {
            textField.setStyle("-jfx-focus-color: #FF0000; -jfx-unfocus-color: #FF0000;");
        } else {
            textField.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        }
    }

    public void searchCard() {
        String cardName = searchingCardNameTxt.getText();
        if (cardName.equals("")) {
            changeAsWrong(searchingCardNameTxt, searchCardBtn, true);
            return;
        }
        try {
            List<Card> cards = ClientManager.searchMyCard(cardName);
            cards.forEach(card -> {
                cardList.getItems().clear();
                addCardToCollectionList(card);
            });
        } catch (Collection.CardNotFoundException e) {
            changeAsWrong(searchingCardNameTxt, searchCardBtn, true);
        }

    }

    public void createNewDeck() {
        String deckName = newDeckNameTxt.getText();
        if (deckName.equals("")) {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, true);
        }
        try {
            ClientManager.createDeck(deckName);
            JFXButton newDeckBtn = new JFXButton(deckName);
            newDeckBtn.setStyle("-fx-padding: 1em 3em;" +
                    "    -fx-font-size: 1em;" +
                    "    -fx-border-radius: 0.3em;" +
                    "    -fx-font-weight: bold;" +
                    "    -fx-pref-width: 420;" +
                    "    -fx-background-color: #f36f20;"); // TODO: 6/11/19 tamiz kari haye css
            newDeckBtn.setOnMouseClicked(event -> {
                selectedDeckCardList.getItems().clear();
                try {
                    ClientManager.getDeck(deckName).getCards().forEach(card -> {
                        int number = 2; // TODO: 6/12/19   //ClientManager.getNumberOfCard(card);
                        String cardBtnText = card.getName();
                        if (number > 1)
                            cardBtnText += " ×" + number;
                        JFXButton cardBtn = new JFXButton(cardBtnText);
                        newDeckBtn.setStyle("-fx-padding: 1em 3em;" +
                                "    -fx-font-size: 1em;" +
                                "    -fx-border-radius: 0.3em;" +
                                "    -fx-font-weight: bold;" +
                                "    -fx-pref-width: 420;" +
                                "    -fx-background-color: #f36f20;"); // TODO: 6/11/19 tamiz kari haye css
                        selectedDeckCardList.getItems().add(cardBtn);
                    });
                } catch (Account.DeckNotFoundException ignored) {
                }
                selectedDeckCardList.setVisible(true);
                selectedDeckName = newDeckBtn.getText();
            });
            deckList.getItems().add(newDeckBtn);
        } catch (Account.DeckExistsException e) {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, true);
        } catch (Account.NotLoggedInException ignored) {
        }
    }

    public void selectDeck(MouseEvent mouseEvent) {
        selectedDeckName = deck.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchingCardNameTxt.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");

        ClientManager.getMyCollection().getCardsList().forEach(card -> addCardToCollectionList(card));


        searchingCardNameTxt.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.equals("")){
                cardList.getItems().clear();
                ClientManager.getMyCollection().getCardsList().forEach(card -> {
                    addCardToCollectionList(card);
                });
            }
            changeAsWrong(searchingCardNameTxt, searchCardBtn, false);
            try {
                cardList.getItems().clear();
                ClientManager.searchMyCard(newValue).forEach(card -> {
                    addCardToCollectionList(card);
                });
            } catch (Collection.CardNotFoundException ignored) { }
        }));

        newDeckNameTxt.textProperty().addListener(((observable, oldValue, newValue) -> {
            changeAsWrong(newDeckNameTxt, saveDeckBtn, false);
        }));

        selectedDeckCardList.setVisible(false);
    }

    private void addCardToCollectionList(Card card) {
        JFXButton button = new JFXButton(card.getName());
        button.setStyle("-fx-padding: 1em 3em;" +
                "    -fx-font-size: 1em;" +
                "    -fx-border-radius: 0.3em;" +
                "    -fx-font-weight: bold;" +
                "    -fx-pref-width: 420;" +
                "    -fx-background-color: #f36f20;"); // TODO: 6/11/19 tamiz kari haye css
        button.setOnMouseClicked(event -> {
            if (selectedDeckName == null) {
                Graphics.alert("Error", "No deck selected", "Please select a deck then " +
                        "try for adding card to it");
                return;
            }
            String cardId = button.getText();
            try {
                ClientManager.addCardToDeck(cardId, selectedDeckName);
                if (ClientManager.hasCard(cardId)) {
                    for (Object obj : selectedDeckCardList.getItems()) {
                        JFXButton btn = (JFXButton) obj;
                        if (btn.getText().equals(cardId)) {
                            int number = ClientManager.getNumberOfCard(card);
                            String cardBtnText = card.getName();
                            if (number > 1)
                                cardBtnText += " ×" + number;
                            btn.setText(cardBtnText);
                            break;
                        }
                    }
                } else {
                    JFXButton btn = new JFXButton(cardId);
                    btn.setStyle("-fx-padding: 1em 3em;" +
                            "    -fx-font-size: 1em;" +
                            "    -fx-border-radius: 0.3em;" +
                            "    -fx-font-weight: bold;" +
                            "    -fx-pref-width: 420;" +
                            "    -fx-background-color: #f36f20;"); // TODO: 6/11/19 tamiz kari haye css
                    selectedDeckCardList.getItems().add(btn);
                }
            } catch (Deck.HeroNotExistsInDeckException e) {
                Graphics.alert("Sorry", "Can't add card to deck!",
                        "You should have one hero in your deck");
            } catch (Deck.DeckFullException e) {
                Graphics.alert("Sorry", "Can't add card to deck!",
                        "This dock is full.");
            } catch (Deck.HeroExistsInDeckException e) {
                Graphics.alert("Sorry", "Can't add card to deck!",
                        "You can't have more than one hero in your deck");
            } catch (Collection.CardNotFoundException | Account.DeckNotFoundException ignored) {
            }

        });
        cardList.getItems().add(button);
    }


}
