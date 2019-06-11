package views.graphics;

import com.jfoenix.controls.JFXButton;
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
    String selectedDeckName;

    private void changeAsWrong(JFXTextField textField, boolean isWrong) {
        if (isWrong) {
            textField.setStyle("-jfx-focus-color: #FF0000; -jfx-unfocus-color: #FF0000;");
        } else {
            textField.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        }
    }


    public void searchCard() {
        String cardName = searchingCardNameTxt.getText();
        try {
            List<Card> cards = ClientManager.searchMyCard(cardName);
            cards.forEach(
                    (card) -> Output.log(card.toString())
            );
        } catch (Collection.CardNotFoundException e) {
            changeAsWrong(searchingCardNameTxt, true);
        }

    }

    public void createNewDeck() {
        String deckName = newDeckNameTxt.getText();
        try {
            ClientManager.createDeck(deckName);
            Output.log(Log.DECK_CREATED);
        } catch (Account.NotLoggedInException e) {
            Output.err(Error.NOT_LOGGED_IN);
        } catch (Account.DeckExistsException e) {
            Output.err(Error.DECK_EXISTS);
        }
    }

    public static void deleteDeck(Matcher matcher) {
        String deckName = matcher.group("name");
        try {
            Manager.deleteDeck(deckName);
            Output.log(Log.DECK_DELETED);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }
    }

    public void addCardToDeck() {
        String cardId = card1.getText();
        String deckName = selectedDeckName;
        try {
            ClientManager.addCardToDeck(cardId, deckName);
            Output.log(Log.CARD_ADDED_TO_DECK);
        } catch (Deck.HeroNotExistsInDeckException e) {
            Output.err(Error.HERO_NOT_EXISTS_IN_DECK);
        } catch (Deck.DeckFullException e) {
            Output.err(Error.DECK_FULL_EXCEPTION);
        } catch (Deck.HeroExistsInDeckException e) {
            Output.err(Error.HERO_EXISTS_IN_DECK);
        } catch (Collection.CardNotFoundException | Account.DeckNotFoundException ignored) { }

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchingCardNameTxt.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");

        searchingCardNameTxt.textProperty().addListener(((observable, oldValue, newValue) -> {
            changeAsWrong(searchingCardNameTxt, false);
        }));
    }

    public void selectDeck(MouseEvent mouseEvent) {
        selectedDeckName = deck.getText();
    }
}
