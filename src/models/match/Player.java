package models.match;

import models.Account;
import models.cards.*;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    public Account account;
    public Deck deck;
    public ArrayList<Card> hand = new ArrayList<>();
    public int manaPoint = 2;
    public Card selectedCard;

    private void makeHand() {
        deck.cards.forEach((card, count) -> {
            for (int i = 0; i < count; i++) {
                Card newCard = new Card();
                if (card instanceof Spell)
                    newCard = new Spell((Spell) card);
                if (card instanceof Minion)
                    newCard = new Minion((Minion) card);
                if (card instanceof Hero)
                    newCard = new Hero((Hero) card);
                hand.add(newCard);
            }
        });
        Collections.shuffle(hand);
    }

    public Player(Account account) {
        this.account = account;
        this.deck = account.mainDeck;
        makeHand();
    }
}
