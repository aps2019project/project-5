package models;

import models.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand extends Collection{
    private Card nextCard;

    public Card getNextCard() {
        return nextCard;
    }

    public void setNextCard(Card nextCard) {
        this.nextCard = nextCard;
    }

}
