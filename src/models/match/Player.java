package models.match;

import models.Account;
import models.cards.Deck;

public class Player {
    public Account account;
    public Deck deck;

    public Player(Account account) {
        this.account = account;
        this.deck = account.mainDeck;
    }
}
