package controllers;

import models.Account;
import models.Deck;
import models.Shop;
import models.cards.Card;
import models.exceptions.CardNotFoundException;
import models.exceptions.ItemsFullException;
import models.exceptions.NotEnoughDrakeException;
import models.match.Match;
import views.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

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

    public int searchCard (Matcher matcher) throws CardNotFoundException {
        String cardName = matcher.group("cardName");
        Card card = null;
        try {
            card = shop.searchCard(cardName);
            return card.getID();
        } catch (CardNotFoundException noCardException) {
            throw noCardException;
        }
    }

    public void buy (Matcher matcher) throws CardNotFoundException, NotEnoughDrakeException, ItemsFullException {
        String cardName = matcher.group("cardName");
        try {
            shop.buy(account, cardName);
        } catch (Exception e) {
            throw e;
        }
        Output.log("buying successful.");
    }

    public void sell (Matcher matcher) throws CardNotFoundException{
        int id = Integer.parseInt(matcher.group("id"));
        try {
            shop.sell(account, id);
        } catch (CardNotFoundException noCard) {
            throw noCard;
        }
        Output.log("selling successful");
    }
}
