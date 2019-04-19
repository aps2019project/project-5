package models;

import models.cards.Card;
import models.items.Item;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Player {
    private Account account;
    private Deck deck;
    private Deque<Card> hand;
    private Card selectedCard;
    private int flags;
    private List<Item> collectedItems=new ArrayList<>();
    private ArrayList<Card> graveYard;
    private Player(Account account){
        this.account=account;
        this.deck=account.getDeck();
    }

    public ArrayList<Card> getGraveYard() { return graveYard; }
    public Deque<Card> getHand() { return hand; }
    public Card getSelectedCard() { return selectedCard; }
    public void insertCard(Card card) {

    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void incrementFlags() {
        this.flags += flags;
    }

    public void setCollectedItems(Item collectedItems) {
        this.collectedItems.add(collectedItems);
    }
}
