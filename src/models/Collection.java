package models;

import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.Flag;
import models.items.UsableItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static views.Error.*;

public class Collection {

    protected Map<Card, Integer> cards = new HashMap<>();

    public Collection() {
    }

    public Collection(Map<Card, Integer> cards) {
        this.cards = new HashMap<>(cards);
    }

    public void addCards(Map<Card, Integer> cards) {
        this.cards.putAll(cards);
    }

    public int getNumberOf(Card card) {
        return cards.get(card);
    }

    public void addCards(List<Card> cards) {
        cards.forEach(this::addCard);
    }

    public List<Card> getCardsList() {
        return ListToMap(cards);
    }

    public Map<Card, Integer> getCardsMap() {
        return cards;
    }

    public void addCard(Card card) {
        if (cards.containsKey(card)){
            int num = cards.get(card);
            cards.replace(card, num, num + 1);
            return;
        }
        this.cards.put(card, 1);
    }

    private static List<Card> ListToMap(Map<Card, Integer> map) {
        return new ArrayList<>(map.keySet());
    }

    public List<Card> filterByName(String keyword) {
        String regex = String.format("(?i)%s", keyword);
        if (cards.size() == 0)
            return ListToMap(cards);
        return cards.keySet().stream().filter(
                (card) -> {
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(card.getName());
                    return matcher.find();
                }
        ).collect(Collectors.toList());
    }

    public List<Hero> getHeroes() {
        return cards.keySet().stream().filter(
                (card) -> (card instanceof Hero)
        ).map(
                (card) -> ((Hero) card)
        ).collect(Collectors.toList());
    }

    public List<Minion> getMinions() {
        return cards.keySet().stream().filter(
                (card) -> (card instanceof Minion)
        ).map(
                (card) -> ((Minion) card)
        ).collect(Collectors.toList());
    }

    public List<Spell> getSpells() {
        return cards.keySet().stream().filter(
                (card) -> (card instanceof Spell)
        ).map(
                (card) -> ((Spell) card)
        ).collect(Collectors.toList());
    }

    public List<UsableItem> getUsableItems() {
        return cards.keySet().stream().filter(
                (card) -> (card instanceof UsableItem)
        ).map(
                (card) -> ((UsableItem) card)
        ).collect(Collectors.toList());
    }

    public void removeCard(Card card) throws CardNotFoundException {
        if (!cards.containsKey(card))
            throw new CardNotFoundException();
        int number = cards.get(card);
        if(cards.get(card) == 1) {
            cards.remove(card);
            return;
        }
        cards.replace(card, number, number - 1);
    }

    public List<Card> getCardsList(String cardName) throws CardNotFoundException {
        List<Card> foundCards = filterByName(cardName);
        if (foundCards.size() == 0)
            throw new CardNotFoundException();
        return foundCards;
    }

    public Card getCard(String cardName) throws CardNotFoundException {
        List<Card> cards = filterByName("^" + cardName + "$").stream().map(
                marketObject -> (Card) marketObject
        ).collect(Collectors.toList());
        if (cards.size() == 0) {
            throw new CardNotFoundException();
        }
        return cards.get(0);
    }

    public List<Flag> getFlags() {
        return cards.keySet().stream()
                .filter(
                        card -> card instanceof Attacker
                ).map(
                        card -> ((Attacker) card).getFlag()
                ).collect(Collectors.toList());
    }

    public Card getCardByID(String cardID) throws CardNotFoundException {
        for (Card card : cards.keySet()) {
            if (card.getID().equalsIgnoreCase(cardID))
                return card;
        }
        throw new CardNotFoundException();
    }

    public boolean contains(Card card) {
        try {
            getCardByID(card.getID());
        }  catch (CardNotFoundException e) {
            return false;
        }
        return true;
    }

    public static class CollectionException extends Exception {
        CollectionException(String message) {
            super(message);
        }

        CollectionException() {
            super();
        }
    }

    public static class CardNotFoundException extends CollectionException {
        public CardNotFoundException() {
            super(CARD_NOT_FOUND.toString());
        }

        public CardNotFoundException(String message) {
            super(message);
        }
    }

    public static class ItemsFullException extends CollectionException {
        public ItemsFullException() {
            super(ITEMS_ARE_FULL.toString());
        }
    }

}
