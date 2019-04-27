package models;

import models.cards.Card;
import models.exceptions.CardNotFoundException;
import models.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Collection {
    private List<MarketObject> cards = new ArrayList<>();

    public List<MarketObject> getCards() {
        return this.cards;
    }

    public void addCard(MarketObject member) {
        this.cards.add(member);
    }

    public List<MarketObject> filterByName(String pattern) {
        if(cards.size() == 0)
            return cards;
        return cards.stream().filter(
                (card) -> card.getName().matches(pattern)
        ).collect(Collectors.toList());
    }

    public int getItemsNumber() {
        int number = 0;
        for (MarketObject mo : cards ) {
            try {
                Item card = (Item) mo;
                number++;
            } catch (Exception e) {}
        }
        return number;
    }

    public void removeCard(Card card) throws CardNotFoundException {
        if (!cards.remove(card))
            throw new CardNotFoundException("You don't have this card in your collection.");
    }
}
