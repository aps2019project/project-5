package models;

import java.util.*;

public class Account {
    private static Map<String, Account> accounts = new HashMap<>();
    private List<MatchResult> matchHistory = new ArrayList<>();
    private String username;
    private String password;
    private Collection collection;
    private List<Deck> decks = new ArrayList<>();
    private Deck deck;
    private int drake = 15000;
    private int winCount = 0;

    public static final Comparator<Account> compare = Comparator.comparingInt(Account::getWinCount);

    public Deck getDeck() {
        return deck;
    }

    public List<Deck> getDecks() {
        // Gives user's decks
        return this.decks;
    }

    public void addDeck(Deck deck) {
        // Add new deck to user's decks
        this.decks.add(deck);
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

    @Override
    public String toString() {
        return String.format("Username: %s - Wins: %d", username, winCount);
    }


    public void addMatchResult(MatchResult matchResult) {
        matchHistory.add(matchResult);
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

    public static class UsernameExistsException extends Exception{
        UsernameExistsException(String username) {
            super(String.format("This username exists: %s", username));
        }
    }

}
