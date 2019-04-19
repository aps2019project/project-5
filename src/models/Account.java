package models;

import models.Collection;
import models.Deck;
import models.MatchResult;

import java.util.*;

public class Account {
    private static Map<String, Account> accounts = new HashMap();

    private List<MatchResult> matchHistory;
    private String username;
    private String password;
    private Collection collection;
    private List<Deck> decks;
    private Deck deck;
    private int drake=15000;
    private int winCount = 0;

    public static final Comparator<Account> compare = Comparator.comparingInt(Account::getWinCount);

    public static Account getUser(String username, String password) {
        for (Map.Entry<String , Account> entry: accounts.entrySet()){
            if (entry.getValue().username.equals(username) && entry.getValue().password.equals(password)){
                return entry.getValue();
            }

        }
        return null;
    }

    public void incrementDrake(int drake){
        this.drake+=drake;
    }


    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static void AddUser(Account user) {
        accounts.putIfAbsent(user.username, user);
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

    public ArrayList<Account> getRanking() {
        ArrayList<Account> ranking = new ArrayList<Account>(accounts.values());
        ranking.sort(compare);
        return ranking;
    }

    public void addMatchResult(MatchResult matchResult){
        matchHistory.add(matchResult);
    }
}
