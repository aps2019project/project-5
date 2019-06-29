package models.cards;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Collection {
    private Map<Card, Integer> cards = new HashMap<>();

    public Map<Card, Integer> filter(Class cardClass, String query) {
        Map<Card, Integer> result = new HashMap<>();
        for (Map.Entry<Card, Integer> card : cards.entrySet()) {
            if (!card.getKey().getClass().isInstance(cardClass) && card.getKey().getClass() != cardClass)
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

    public int count(Card card) {
        return cards.getOrDefault(card, 0);
    }
}