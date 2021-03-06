package models;

import models.cards.Collection;
import models.cards.Deck;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Account {
    public String username, password;
    public String loginToken;
    public int drake = 15000;
    public Map<String, Deck> decks = new HashMap<>();
    public Deck mainDeck;
    public Collection cards = new Collection();

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return username.equals(account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
