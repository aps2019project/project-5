package models;

import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.UsableItem;
import views.Error;

import java.util.*;
import java.util.stream.Collectors;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();
    private String name;

    public boolean isComplete() {
        return false;
    }
    public void addCard(Card card) throws HeroExistsInDeckException, DeckFullException, HeroNotExistsInDeckException {
        if (this.getHero() != null) {
            throw new HeroExistsInDeckException(card.getName(), this.name);
        }
        if (cards.size() == 20) {
            throw new DeckFullException(this.name);
        }
        if (cards.size() == 19 && !(card instanceof Hero) && this.getHero() == null) {
            throw new HeroNotExistsInDeckException();

        }

        cards.add(card);

    }

    public Deck(Deck deck) {
        for (Card card : deck.getCards()) {
            this.getCards().add(card);
        }
        this.name = deck.getName();
    }

    public ArrayList<Card> getCards() {
        return cards;
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

    public Hero getHero() {
        List<Card> heroCards = cards.stream().filter(
                (card) -> (card instanceof Hero)
        ).collect(Collectors.toList());
        try {
            return (Hero) heroCards.get(0);
        } catch (Exception e) {
            return null;
        }
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
        result.append("Heroes :\n\t");
        if (this.getHero() != null)
            result.append(this.getHero().toString());
        result.append("\nItems :\n");
        for (UsableItem item : this.getItems()) {
            result.append("\t");
            result.append(item.toString());
            result.append("\n");
        }
        result.append("Cards : \n");
        this.cards.stream().filter(card -> card instanceof Spell || card instanceof Minion)
                .forEach(card -> {
                            result.append(card.toString());
                            result.append("\n\t");
                        }
                );
        return result.toString();

    }

    public boolean isValid() {
        return !(cards.size() != 20 || this.getHero() != null);
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

    public static class HeroNotExistsInDeckException extends Exception {
        public HeroNotExistsInDeckException() {
            super("Hero is not existed");
        }
    }
}