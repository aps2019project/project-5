package models;

import models.cards.Card;
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();

    public boolean isComplete() { return false; }
    public void addCard(Card card) {}
    public void removeCard(Card card) {}
}