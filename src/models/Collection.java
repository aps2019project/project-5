package models;

import models.cards.Card;
import models.exceptions.CardNotFoundException;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.Item;
import models.items.UsableItem;

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

    public List<Hero> getHeroes() {
        return cards.stream().filter(
                (card) -> (card instanceof Hero)
        ).map(
                (card) -> ((Hero) card)
        ).collect(Collectors.toList());
    }

    public List<Minion> getMinions() {
        return cards.stream().filter(
                (card) -> (card instanceof Minion)
        ).map(
                (card) -> ((Minion) card)
        ).collect(Collectors.toList());
    }

    public List<Spell> getSpells() {
        return cards.stream().filter(
                (card) -> (card instanceof Spell)
        ).map(
                (card) -> ((Spell) card)
        ).collect(Collectors.toList());
    }

    public List<UsableItem> getUsableItems() {
        return cards.stream().filter(
                (card) -> (card instanceof UsableItem)
        ).map(
                (card) -> ((UsableItem) card)
        ).collect(Collectors.toList());
    }
}
