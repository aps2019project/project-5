package models.auth;

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
    private int drake;
    private int winCount = 0;

    public static final Comparator<Account> compare = Comparator.comparingInt(Account::getWinCount);

    public Account getUser(String username, String password) { return null; }
    public void AddUser(Account user) {}
    public List<MatchResult> getMatchHistory() { return matchHistory; }
    public int getWinCount() { return winCount; }

    public ArrayList<Account> getRanking() {
        ArrayList<Account> ranking = new ArrayList<Account>(accounts.values());
        ranking.sort(compare);
        return ranking;
    }
}
