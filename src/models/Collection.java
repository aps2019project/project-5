package models;

import models.cards.AttackType;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Collection {
    private static List<Card> allCards = new ArrayList<>();
    private List<Card> cards = new ArrayList<>();

    static {
        // TODO: Add all cards (heroes, minions, usable items)
        // TODO: Add allCards.add(new Hero(...));

        allCards.add(new Minion(1,"کماندار فارس", "", 2, 300, 6, 4, AttackType.RANGED));
        allCards.add(new Minion(1,"شمشیرزن فارس", "", 2, 300, 6, 4, AttackType.RANGED));

        System.out.println(allCards.get(0).getName());
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public static List<Card> getAllCards() {
        return allCards;
    }

}
