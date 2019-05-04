package models;

import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.Flag;
import models.items.UsableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static views.Error.*;
import static views.Log.EMPTY_COLLECTION;

public class Collection {


    private List<Card> cards = new ArrayList<>();

    public Collection() {}

    public Collection(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public List<Card> getCardsList() {
        return this.cards;
    }

    public void addCard(Card member) {
        this.cards.add(member);
    }

    public List<Card> filterByName(String keyword) {
        String regex = String.format("(?i)%s", keyword);
        if (cards.size() == 0)
            return cards;
        return cards.stream().filter(
                (card) -> {
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(card.getName());
                    return matcher.find();
                }
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

    public void removeCard(Card card) throws CardNotFoundException {
        if (!cards.remove(card))
            throw new CardNotFoundException();
    }

    public List<Card> getCardsList(String cardName) throws CardNotFoundException {
        List<Card> foundCard = filterByName(cardName);
        if (foundCard.size() == 0)
            throw new CardNotFoundException();
        return foundCard;
    }

    public Card getCard(String cardName) throws CardNotFoundException {
        List<Card> cards = filterByName("^" + cardName + "$").stream().map(
                marketObject -> (Card) marketObject
        ).collect(Collectors.toList());
        if (cards.size() != 1) {
            throw new CardNotFoundException();
        }
        return cards.get(0);
    }

    public List<Flag> getFlags() {
        return cards.stream()
                .filter(
                        card -> card instanceof Attacker
                ).map(
                        card -> ((Attacker) card).getFlag()
                ).collect(Collectors.toList());
    }

    public Card getCard(int cardID) throws CardNotFoundException {
        for (Card card :cards) {
            if (card.getID() == cardID)
                return card;
        }
        throw new CardNotFoundException();
    }

    public static class CardNotFoundException extends Exception {
        public CardNotFoundException() {
            super(CARD_NOT_FOUND.toString());
        }

        public CardNotFoundException(String message) {
            super(message);
        }
    }

    public static class ItemsFullException extends Exception {
        public ItemsFullException() {
            super(ITEMS_ARE_FULL.toString());
        }
    }

    public static class NullCollectionException extends Exception {
        public NullCollectionException() {
            super(EMPTY_COLLECTION.toString());
        }
    }

}
