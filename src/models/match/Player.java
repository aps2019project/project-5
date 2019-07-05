package models.match;

import models.Account;
import models.cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Player {
    public Account account;
    public Deck deck;
    public ArrayList<Card> hand = new ArrayList<>();
    public int manaPoint = 2;
    public Card selectedCard;

    private void makeHand() {
        int cardId = 0;
        for (Map.Entry<Card, Integer> entry : deck.cards.entrySet()) {
            Card card = entry.getKey();
            Integer count = entry.getValue();
            for (int i = 0; i < count; i++) {
                Card newCard = new Card();
                if (card instanceof Spell)
                    newCard = new Spell((Spell) card);
                if (card instanceof Minion)
                    newCard = new Minion((Minion) card);
                if (card instanceof Hero)
                    newCard = new Hero((Hero) card);

                if(newCard instanceof Attacker)
                    ((Attacker) newCard).currentHealth = ((Attacker) newCard).health;

                newCard.playerName = account.username;
                newCard.id = cardId++;
                newCard.canMove = false;
                newCard.isInserted = false;
                System.out.println(newCard.name + "   " + newCard.id + "   " + newCard.playerName);

                hand.add(newCard);
            }
        }
        Collections.shuffle(hand);
    }

    public Player(Account account) {
        this.account = account;
        this.deck = new Deck(account.mainDeck);
        makeHand();
    }
}
