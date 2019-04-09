package models;

import models.cards.Card;
import models.items.Item;
import models.items.UsableItem;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();

    public boolean isComplete() {
        return false;
    }
    public void addCard(Card card) {}
    public void removeCard(Card card) {}
    public void addItem(UsableItem usableItem) {}
    public void removeItem(UsableItem usableItem) {}

}