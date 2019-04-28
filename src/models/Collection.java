package models;

import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.UsableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static views.Error.*;
import static views.Log.EMPTY_COLLECTION;

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

    public void removeCard (Card card) throws CardNotFoundException {
        if(!cards.remove(card))
            throw new CardNotFoundException();
    }

    public List<Card> getCards(String cardName) throws CardNotFoundException {
        List<MarketObject> foundCard = filterByName(cardName);
        if (foundCard.size() == 0)
            throw new CardNotFoundException();
        return foundCard.stream().map(
                marketObject -> (Card) marketObject)
                .collect(Collectors.toList());
    }

    public Card getCard(String cardName) throws CardNotFoundException {
        List<Card> cards = filterByName("^" + cardName + "$").stream().map(
                marketObject -> (Card)marketObject
        ).collect(Collectors.toList());
        if (cards.size() == 0) {
            throw new CardNotFoundException();
        }
        return cards.get(0);
    }

    public static class CardNotFoundException extends Exception{
        public CardNotFoundException () {
            super(CARD_NOT_FOUND.toString());
        }
        public CardNotFoundException (String message) {
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
