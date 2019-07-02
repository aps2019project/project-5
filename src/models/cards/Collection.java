package models.cards;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Collection {
    public Map<Card, Integer> cards = new HashMap<>();

    public Map<Card, Integer> filter(Class cardClass, String query) {
        System.out.println(cardClass);
        Map<Card, Integer> result = new HashMap<>();
        for (Map.Entry<Card, Integer> card : cards.entrySet()) {
            if (cardClass != Card.class && cardClass != card.getKey().getClass())
                continue;
            if (query == null || query.equals("") || card.getKey().name.toLowerCase().contains(query.toLowerCase()))
                result.put(card.getKey(), card.getValue());
        }
        return result;
    }

    public int size() {
        AtomicInteger size = new AtomicInteger(0);
        cards.forEach((card, count) -> {
            size.addAndGet(count);
        });
        return size.get();
    }

    public boolean add(Card card) {
        if (cards.containsKey(card)) {
            cards.replace(card, cards.get(card) + 1);
        } else {
            cards.put(card, 1);
        }
        return true;
    }

    public boolean decrease(Card card) {
        if (!cards.containsKey(card)) {
            return false;
        } else if (cards.get(card) > 1) {
            cards.replace(card, cards.get(card) - 1);
        } else {
            cards.remove(card);
        }
        return true;
    }

    public Card searchCardByName(String name) {
        for (Map.Entry card1 : cards.entrySet()) {
            if (((Card) card1.getKey()).name.equalsIgnoreCase(name)) {
                return (Card) card1.getKey();
            }
        }
        return null;
    }

    public int count(Card card) {
        System.out.println(card);
        System.out.println(cards.getOrDefault(card, 0));
        return cards.getOrDefault(card, 0);
    }
}
