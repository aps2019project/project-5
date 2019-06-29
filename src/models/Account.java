package models;

import models.cards.Deck;
import java.util.HashMap;
import java.util.Map;

public class Account {
    public String username, password;
    public Map<String, Deck> decks = new HashMap<>();
    public Deck mainDeck;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
