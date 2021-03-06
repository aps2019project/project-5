package client.models;

import client.models.cards.Card;

import java.util.ArrayList;

public class Hand {
    private Card nextCard;
    private ArrayList<Card> cards = new ArrayList<>();

    public Card getNextCard() {
        return nextCard;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(String cardID) throws Collection.CardNotFoundException {
        for(Card card : cards)
            if(card.getID().equalsIgnoreCase(cardID))
                return card;
        throw new Collection.CardNotFoundException("Card not found in hand");
    }

    public void setNextCard(Card nextCard) {
        this.nextCard = nextCard;
    }

}
