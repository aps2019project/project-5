package models;

import models.cards.Card;
import models.exceptions.CardNotFoundException;

import java.util.*;

public class Account {
    private static Map<String, Account> accounts = new HashMap<>();
    private List<MatchResult> matchHistory = new ArrayList<>();
    private String username;
    private String password;
    private Collection collection;
    private List<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private int drake = 15000;
    private int winCount = 0;

    public int getDrake() {
        return drake;
    }

    public int getItemsNumber () {
        return collection.getItemsNumber();
    }

    public static final Comparator<Account> compare = Comparator.comparingInt(Account::getWinCount);

    public Deck getMainDeck() {
        return mainDeck;
    }

    public Deck getDeck(String name) throws DeckNotFoundException {
        for(Deck deck : decks)
            if(deck.getName().equals(name))
                return deck;
        throw new DeckNotFoundException(name);
    }

    //for selling a card
    public void removeCardFromCollection (Card card) throws CardNotFoundException {
        try {
            collection.removeCard(card);
        } catch (Exception e){
            throw e;
        }
    }

    // Gives user's decks
    public List<Deck> getDecks() {
        return this.decks;
    }

    // Add new deck to user's decks
    public void addDeck(Deck deck) throws DeckExistsException {
        for(Deck existingDeck : decks)
            if(existingDeck.getName().equals(deck.getName()))
                throw new DeckExistsException(deck.getName());
        this.decks.add(deck);
    }

    public Collection getCollection() {
        return collection;
    }

    public static Account getAccount(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getValue().username.equals(username)) {
                if(entry.getValue().password.equals(password))
                    return entry.getValue();
                else
                    throw new InvalidPasswordException();
            }
        }
        throw new InvalidUsernameException(username);
    }

    public void incrementDrake(int drake) {
        this.drake += drake;
    }


    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private static boolean doesAccountExists(Account account){
        return accounts.containsKey(account.username);
    }

    public static void addAccount(Account user) throws UsernameExistsException {
        if (!doesAccountExists(user))
            accounts.put(user.username, user);
        else throw new UsernameExistsException(user.username);
    }

    public void deleteDeck(String deckName) throws DeckNotFoundException {
        Deck deck = getDeck(deckName);
        decks.remove(deck);
    }

    public List<MatchResult> getMatchHistory() {
        return matchHistory;
    }

    public int getWinCount() {
        return winCount;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public static ArrayList<Account> getRanking() {
        ArrayList<Account> ranking = new ArrayList<>(accounts.values());
        ranking.sort(compare);
        return ranking;
    }

    public void addCardToCollection (Card card) {
        collection.addCard(card);
    }

    @Override
    public String toString() {
        return String.format("Username: %s - Wins: %d", username, winCount);
    }


    public void addMatchResult(MatchResult matchResult) {
        matchHistory.add(matchResult);
    }

    public Card getCard(int id) {
        for (MarketObject mo:collection.getCards()) {
            Card card = (Card) mo;
            if(card.getID() == id){
                return card;
            }
        }
        return null;
    }

    public static class InvalidUsernameException extends Exception {
        InvalidUsernameException(String username) {
            super(String.format("Username not found: %s", username));
        }
    }

    public static class InvalidPasswordException extends Exception {
        InvalidPasswordException() {
            super("Wrong password.");
        }
    }

    public static class NotLoggedInException extends Exception {
        public NotLoggedInException() {
            super("You must log in.");
        }
    }

    public static class UsernameExistsException extends Exception {
        UsernameExistsException(String username) {
            super(String.format("This username exists: %s", username));
        }
    }

    public static class DeckExistsException extends Exception {
        DeckExistsException(String name) {
            super(String.format("Deck '%s' exists.", name));
        }
    }

    public static class DeckNotFoundException extends Exception {
        DeckNotFoundException(String name) {
            super(String.format("Deck '%s' not found for this user.", name));
        }
    }
}
