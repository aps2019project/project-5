package controllers;

import models.*;
import models.cards.Card;
import models.Collection.CardNotFoundException;
import models.Collection.ItemsFullException;
import models.Account.NotEnoughDrakeException;
import models.cards.Minion;
import models.match.Match;
import models.match.MultiFlagMatch;
import views.Input;
import views.InputAI;
import views.Log;
import views.Output;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private static Account account;
    private static Match playingMatch;
    private static Shop shop = Shop.getInstance();

    public static void setState(boolean isStory) {

        playingMatch.setState(isStory);
    }

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

    public static void buy(String cardName) throws CardNotFoundException, NotEnoughDrakeException, ItemsFullException {
        shop.buy(account, cardName);
    }

    public static void sell(String cardName) throws CardNotFoundException {
        shop.sell(account, cardName);
        for (Deck deck : account.getDecks()) {
            deck.getCards().stream()
                    .filter(card -> card.getName().equalsIgnoreCase(cardName))
                    .forEach(card -> deck.getCards().remove(card));

        }
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

    public static String getMatchInfo() {
        return "Player 1 mana: " + playingMatch.getPlayer1().getMana() +
                "Player 2 mana: " + playingMatch.getPlayer2().getMana() +
                playingMatch.getInfo();
    }

    public static boolean validateDeck(String deckName) throws Account.DeckNotFoundException {
        Deck deck = account.getDeck(deckName);
        return deck.isValid();
    }

    public static void selectDeck(String deckName) throws Account.DeckNotFoundException {
        Deck deck = account.getDeck(deckName);
        account.setMainDeck(deck);
    }

    public static Input getInput() {
        if (playingMatch == null)
            return Input.getInstance();
        if (playingMatch.isAIMode() && playingMatch.getTurn() % 2 == 1)
            return InputAI.getInstance();
        return Input.getInstance();
    }

    public static String getAIMove() {
        playingMatch.getPlayer2().decide();
        return playingMatch.getPlayer2().getDecision();
    }

    public static Player getActivePlayer() {
        return playingMatch.getActivePlayer();
    }

    public static Player getInActivePlayer() {
        return playingMatch.getInActivePlayer();
    }

    public static boolean canPlay(String username) throws Account.InvalidUsernameException, Account.CantPlayWithYourselfException {
        Account playerAccount = Account.getAccounts().get(username);
        if(playerAccount == null)
            throw new Account.InvalidUsernameException(username);
        if(username.equals(account.getUsername()))
            throw new Account.CantPlayWithYourselfException();
        if(playerAccount.getMainDeck() == null)
            return false;
        return playerAccount.getMainDeck().isValid();
    }

    public static List<Minion> showMyMinions() {
        return playingMatch.showMyMinions();
    }

    public static List<Minion> showOponentMinions() {
        return playingMatch.showOponentMinions();
    }
}
