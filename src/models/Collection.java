package models;

import models.cards.Card;
import models.cards.Hero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Collection {
    private static Map<String, Card> allCards = new HashMap<>();
    private Map<String, Card> cards = new HashMap<>();

    static {
        // TODO: Add all cards (heroes, minions, usable items)
        // TODO: Add cards like: allCards.put("Name", new Hero(...));

    }

    public Map<String, Card> getCards() {
        return this.cards;
    }

    public static Map<String, Card> getAllCards() {
        return allCards;
    }

}
