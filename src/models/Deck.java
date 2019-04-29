package models;

import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.UsableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();
    private String name;

    public boolean isComplete() {
        return false;
    }

    public void addCard(Card card) {

    }

    public void removeCard(Card card) {

    }

    public void addItem(UsableItem usableItem) {

    }

    public void removeItem(UsableItem usableItem) {

    }

    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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

    public List<UsableItem> getItems() {
        return cards.stream().filter(
                (card) -> (card instanceof UsableItem)
        ).map(
                (card) -> ((UsableItem) card)
        ).collect(Collectors.toList());
    }

    public List<Spell> getSpells() {
        return cards.stream().filter(
                (card) -> (card instanceof Spell)
        ).map(
                (card) -> ((Spell) card)
        ).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.name);
        // TODO: Add deck cards.
        return result.toString();
    }
}