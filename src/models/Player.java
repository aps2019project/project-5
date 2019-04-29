package models;

import models.cards.AttackType;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.items.Flag;
import models.items.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Player {
    private Account account;
    private Deck deck;
    private Deque<Card> hand;
    private Card selectedCard;
    private int flags;
    private List<Item> collectedItems=new ArrayList<>();
    private ArrayList<Card> graveYard;
    private List<Card> activeCards;

    private Player(Account account){
        this.account=account;
        this.deck=account.getMainDeck();
    }


    public ArrayList<Card> getGraveYard() { return graveYard; }
    public Deque<Card> getHand() { return hand; }
    public Card getSelectedCard() { return selectedCard; }
    public void insertCard(Card card) {

    }

    public Deck getDeck() {
        return deck;
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


    public List<Flag> getFlags() {
        return activeCards
                .stream()
                .filter(
                card -> card instanceof Attacker)
                .map(
                card -> ((Attacker)card).getFlag()
                )
                .collect(Collectors.toList());
    }

    public boolean hasFlag() {
        return getFlags().size() > 0;
    }
}
