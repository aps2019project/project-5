package models;

import models.cards.Card;
import models.items.UsableItem;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();
    private String name;

    public boolean isComplete() {
        return false;
    }

    public void addCard(Card card) {

    }

    public void removeCard(Card card) {

    }

    public void addItem(UsableItem usableItem) {

    }

    public void removeItem(UsableItem usableItem) {

    }

    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}