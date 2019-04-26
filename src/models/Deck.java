package models;

import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.UsableItem;

import java.util.ArrayList;

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

    public ArrayList<Hero> getHeroes() {
        ArrayList<Hero> heroes = new ArrayList<>();
        for(Card card : cards)
            if(card instanceof Hero)
                heroes.add((Hero) card);
        return heroes;
    }

    public ArrayList<UsableItem> getItems() {
        ArrayList<UsableItem> items = new ArrayList<>();
        for(Card card : cards)
            if(card instanceof UsableItem)
                items.add((UsableItem) card);
        return items;
    }

    public ArrayList<Spell> getSpells() {
        ArrayList<Spell> spells = new ArrayList<>();
        for(Card card : cards)
            if(card instanceof Spell)
                spells.add((Spell) card);
        return spells;
    }

    public ArrayList<Minion> getMinions() {
        ArrayList<Minion> minions = new ArrayList<>();
        for(Card card : cards)
            if(card instanceof Minion)
                minions.add((Minion) card);
        return minions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.name);

        return result.toString();
    }
}