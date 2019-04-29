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

    public void addCard(Card card) throws CardExistsInDeckException, HeroExistsInDeckException, DeckFullException {
        if (hasCard(card.getName())) {
            throw new CardExistsInDeckException(card.getName(), this.name);
        }
        if (this.getHeroes().size() == 1) {
            throw new HeroExistsInDeckException(card.getName(), this.name);
        }
        if (cards.size() == 20) {
            throw new DeckFullException(this.name);
        }
        cards.add(card);

    }

    public boolean hasCard(String cardName) {
        return (this.cards.stream().filter(
                card -> card.getName().equals(cardName)
        ).count() == 1);
    }

    public void removeCard(Card card) throws Collection.CardNotFoundException {
        if (!hasCard(card.getName())) {
            throw new Collection.CardNotFoundException();
        }
        cards.remove(card);
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

    public static class CardExistsInDeckException extends Exception {
        public CardExistsInDeckException(String cardName, String deckName) {
            super(String.format("card '%s' exists in deck '%s'", cardName, deckName));
        }
    }

    public static class HeroExistsInDeckException extends Exception {
        public HeroExistsInDeckException(String cardName, String deckName) {
            super(String.format("card '%s' exists in deck '%s'", cardName, deckName));
        }
    }

    public static class DeckFullException extends Exception {
        public DeckFullException(String deckName) {
            super(String.format("deck '%s' is full!", deckName));
        }
    }
}