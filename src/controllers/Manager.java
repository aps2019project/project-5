package controllers;

import models.Account;
import models.Deck;
import models.Shop;
import models.cards.Card;
import models.exceptions.CardNotFoundException;
import models.match.Match;
import views.Output;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private static Account account;
    private static Match playingMatch;
    public Shop shop = Shop.getInstance();

    public static Account getAccount() {
        return account;
    }

    public static void addAccount(Account account) throws Account.UsernameExistsException {
        Account.addAccount(account);
    }

    public static void login(String username, String password) throws Account.InvalidPasswordException, Account.InvalidUsernameException {
        account = Account.getAccount(username, password);
    }

    public static ArrayList<Account> getLeaderboard() {
        return Account.getRanking();
    }

    public static Match getPlayingMatch() {
        return playingMatch;
    }

    public static void createDeck(String name) throws Account.NotLoggedInException, Account.DeckExistsException {
        if(account == null) {
            throw new Account.NotLoggedInException();
        }
        Deck deck = new Deck(name);
        account.addDeck(deck);
    }

    public static void deleteDeck(String name) throws Account.DeckNotFoundException {
        account.deleteDeck(name);
    }

    public static List<Deck> getDecks() throws Account.NotLoggedInException {
        if(account == null)
            throw new Account.NotLoggedInException();
        return account.getDecks();
    }

    public int searchCard (String cardName) throws CardNotFoundException {
        Card card = null;
        try {
            card = shop.searchCard(cardName);
            return card.getID();
        } catch (CardNotFoundException noCardException) {
            throw noCardException;
        }
    }
}
