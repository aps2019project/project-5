package models;

import models.cards.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private Card nextCard;

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card getNextCard() {
        return nextCard;
    }

}
