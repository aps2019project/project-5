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
import java.util.regex.Matcher;
import java.util.stream.Collectors;

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
        if (account == null) {
            throw new Account.NotLoggedInException();
        }
        Deck deck = new Deck(name);
        account.addDeck(deck);
    }

    public static void deleteDeck(String name) throws Account.DeckNotFoundException {
        account.deleteDeck(name);
    }

    public static List<Deck> getDecks() throws Account.NotLoggedInException {
        if (account == null)
            throw new Account.NotLoggedInException();
        return account.getDecks();
    }

    public static List<Card> searchCardInShop(String cardName) throws CardNotFoundException {
        return shop.searchCards(cardName);
    }

    public static int searchCard(String cardName) throws CardNotFoundException {
        Card card = shop.searchCard(cardName);
        return card.getID();
    }

    public static void buy(String cardName) throws CardNotFoundException, NotEnoughDrakeException, ItemsFullException {
        shop.buy(account, cardName);
    }

    public static void sell(String cardName) throws CardNotFoundException {
        shop.sell(account, cardName);
        for (Deck deck : account.getDecks()) {
            deck.getCards().stream()
                    .filter(card -> card.getName().equals(cardName))
                    .forEach(card -> deck.getCards().remove(card));

        }
        Output.log(Log.SELLING_SUCCESSFUL);
    }

    public static Collection getShopCollection() throws Collection.NullCollectionException {
        Collection collection = shop.getCardsCollection();
        if (collection == null)
            throw new Collection.NullCollectionException();
        return shop.getCardsCollection();
    }


    public static Collection getMyCollection() throws Collection.NullCollectionException {
        Collection collection = account.getCollection();
        if (collection == null)
            throw new Collection.NullCollectionException();
        return collection;
    }

    public static List<Card> searchMyCard(String cardName) throws CardNotFoundException {
        return account.getCollection().getCards(cardName);
    }

    public static void addCardToDeck(String cardName, String deckName) throws Account.DeckNotFoundException,
            CardNotFoundException, Deck.HeroExistsInDeckException, Deck.DeckFullException, Deck.HeroNotExistsInDeckException {
        Card card = account.getCollection().getCard(cardName);
        Deck deck = account.getDeck(deckName);
        deck.addCard(card);

    }

    public static void removeCardFromDeck(String cardName, String deckName) throws CardNotFoundException,
            Account.DeckNotFoundException {
        Card card = account.getCollection().getCard(cardName);
        Deck deck = account.getDeck(deckName);
        deck.removeCard(card);
    }

    public static boolean validateDeck(String deckName) throws Account.DeckNotFoundException {
        Deck deck = account.getDeck(deckName);
        return deck.validateDeck();
    }

    public static void selectDeck(String deckName) throws Account.DeckNotFoundException {
        Deck deck = account.getDeck(deckName);
        account.setMainDeck(deck);
    }
}
