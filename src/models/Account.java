package models;

import models.cards.Card;

import java.util.*;

public class Account {
    private static Map<String, Account> accounts = new HashMap();

    private List<MatchResult> matchHistory;
    private String username;
    private String password;
    private Collection<Card> collection;
    private List<Deck> decks;
    private Deck deck;
    private int drake;
    private int winCount = 0;

    public static final Comparator<Account> compare = Comparator.comparingInt(Account::getWinCount);

    public Account getUser(String username, String password) {
        return null;
    }

    public void AddUser(Account user) {
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
        ArrayList<Account> ranking = new ArrayList<>(accounts.values());
        ranking.sort(compare);
        return ranking;
    }
}
