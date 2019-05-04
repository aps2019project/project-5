package models;

import models.cards.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private Card nextCard;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getNextCard() {
        return nextCard;

    }

    public void setNextCard(Card nextCard) {
        this.nextCard = nextCard;
    }

}
