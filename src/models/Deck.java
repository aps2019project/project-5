package models;

import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.UsableItem;

import java.util.*;
import java.util.stream.Collectors;

public class Deck {
    private String name;
    private List<Card> cards = new ArrayList<>();


    public void addCard(Card card) throws HeroExistsInDeckException, DeckFullException, HeroNotExistsInDeckException {
        if (this.getHero() != null && card instanceof Hero)
            throw new HeroExistsInDeckException(card.getName(), this.name);
        if (cards.size() == 20)
            throw new DeckFullException(this.name);
        if (cards.size() == 19 && !(card instanceof Hero) && this.getHero() == null)
            throw new HeroNotExistsInDeckException();
        cards.add(card);
    }

    public Deck(Deck deck) {
        this.cards.addAll(deck.getCards());
        this.name = deck.getName();
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean hasCard(String cardName) {
        return (this.cards.stream().filter(
                card -> card.getName().equalsIgnoreCase(cardName)
        ).count() >= 1);
    }

    public void removeCard(Card card) throws Collection.CardNotFoundException {
        if (!hasCard(card.getName())) {
            throw new Collection.CardNotFoundException();
        }
        cards.remove(card);
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
        result.append("Heroes :\n");
        if (this.getHero() != null)
            result.append("\t").append(this.getHero().toString()).append("\n");
        result.append("Items :\n");
        for (UsableItem item : this.getItems()) {
            result.append("\t");
            result.append(item.toString());
            result.append("\n");
        }
        result.append("Cards : ");
        this.cards.stream().filter(card -> card instanceof Spell || card instanceof Minion)
                .forEach(card -> {
                            result.append("\n\t");
                            result.append(card.toString());
                        }
                );
        return result.toString();
    }

    public boolean isValid() {
        return !(cards.size() < 20 || this.getHero() == null);
    }

    public static boolean isValid(Deck deck) {
        return deck != null && deck.getCards().size() == 20 && deck.getHero() != null;
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

    public long countNumberOf(Card card) {
        return cards.stream().filter(tmpCard -> tmpCard.getName().equals(card.getName())).count();
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