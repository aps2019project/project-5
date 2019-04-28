package controllers;

import models.Account;
import models.Collection;
import models.Deck;
import models.Shop;
import models.cards.Card;
import models.Collection.CardNotFoundException;
import models.Collection.ItemsFullException;
import models.Account.NotEnoughDrakeException;
import models.match.Match;
import views.Log;
import views.Output;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private static Account account;
    private static Match playingMatch;
    private static Shop shop = Shop.getInstance();

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

    public static List<Card> searchCardInShop(String cardName) throws CardNotFoundException {
        List<Card> foundCards = shop.searchCards(cardName);
        return foundCards;
    }

    public static void buy (String cardName) throws CardNotFoundException, NotEnoughDrakeException, ItemsFullException {
        shop.buy(account, cardName);
        Output.log(Log.BUYING_SUCCESSFUL);
    }

    public static void sell (int cardID) throws CardNotFoundException{
        shop.sell(account, cardID);
        Output.log(Log.SELLING_SUCCESSFUL);
    }

    public static Collection getShopCollection() throws Collection.NullCollectionException {
        Collection collection = shop.getCardsCollection();
        if(collection == null)
            throw new Collection.NullCollectionException();
        return shop.getCardsCollection();
    }


    public static Collection getMyCollection() throws Collection.NullCollectionException {
        Collection collection = account.getCollection();
        if(collection == null)
            throw new Collection.NullCollectionException();
        return collection;
    }

    public static List<Card> searchMyCard(String cardName) throws CardNotFoundException {
        List<Card> foundCards = account.getCollection().getCards(cardName);
        return foundCards;
    }
}
