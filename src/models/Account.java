package models;

import models.cards.Card;

import java.util.*;
class userNameExistsException extends Exception{
    public userNameExistsException(){
        super("username exists");
    }
}

public class Account {
    private static Map<String, Account> accounts = new HashMap();
    private List<MatchResult> matchHistory;
    private String username;
    private String password;
    private Collection collection;
    private List<Deck> decks;
    private Deck deck;
    private int drake = 15000;
    private int winCount = 0;
    public static userNameExistsException userNameExistsException=new userNameExistsException();
    public static final Comparator<Account> compare = Comparator.comparingInt(Account::getWinCount);

    public static Account getUser(String username, String password) {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getValue().username.equals(username) && entry.getValue().password.equals(password)) {
                return entry.getValue();
            }

        }
        return null;
    }

    public void incrementDrake(int drake) {
        this.drake += drake;
    }


    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static boolean doesAccountExists(Account account){
        for (Map.Entry<String,Account> entry:accounts.entrySet()){
            if (account.getUsername().equals(entry.getKey())){
                return true;
            }
        }
        return false;
    }

    public static void AddUser(Account user) throws userNameExistsException{
        if (!doesAccountExists(user))
            accounts.put(user.username, user);
        else throw userNameExistsException;
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

    public ArrayList<Account> getRanking() {
        ArrayList<Account> ranking = new ArrayList<>(accounts.values());
        ranking.sort(compare);
        return ranking;
    }


    public void addMatchResult(MatchResult matchResult) {
        matchHistory.add(matchResult);
    }
}
