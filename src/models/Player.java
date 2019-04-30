package models;

import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.items.Flag;
import models.items.Item;
import views.Input;
import views.InputAI;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private Account account;
    private Deck deck;
    private Hand hand;
    private Card selectedCard;
    private int flags;
    private List<Item> collectedItems = new ArrayList<>();
    private ArrayList<Card> graveYard;
    private List<Card> activeCards;
    private int mana;

    public int getMana() {
        return mana;
    }

    public void setMana() {
        this.mana = mana;
    }

    private Player(Account account) {
        this.account = account;
        this.deck = account.getMainDeck();
        this.setHand();
        this.setCardsId();
        this.setNextCard();
    }


    public ArrayList<Card> getGraveYard() {
        return graveYard;
    }

    public Hand getHand() {
        return hand;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void insertCard(Card card) {

    }

    public Deck getDeck() {
        return deck;
    }

    public void incrementFlags() {
        this.flags += flags;
    }

    public void setCollectedItems(Item collectedItems) {
        this.collectedItems.add(collectedItems);
    }


    public List<Flag> getFlags() {
        return activeCards
                .stream()
                .filter(
                        card -> card instanceof Attacker
                ).map(
                        card -> ((Attacker) card).getFlag()
                ).collect(Collectors.toList());
    }

    public List<Card> getActiveCards() {
        return activeCards;
    }

    public boolean hasFlag() {
        return getFlags().size() > 0;
    }

    public void setCardsId() {
        int i = 0;
        for (Card card : getDeck().getCards())
            card.setId(++i);
    }

    private void setDeck() {
        Collections.shuffle(deck.getCards());
    }

    private void setHand() {
        for (int i = 0; i < 5; i++) {
            hand.getHand().add(deck.getCards().get(i));
        }
    }

    private void setNextCard() {
        hand.setNextCard(deck.getCards().get(5));
    }


}
