package models.auth;

import models.Collection;
import models.Deck;
import models.Match;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    private static Map<String, Account> accounts = new HashMap();

    private List<Match> matchHistory;
    private String username;
    private String password;
    private Collection collection;
    private List<Deck> decks;
    private Deck deck;
    private int drake;

    public Account getUser(String username, String password) {
        return null;
    }
}
