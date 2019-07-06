package models.cards;

import java.util.Map;

public class Deck extends Collection {
    public String name;

    @Override
    public boolean add(Card card) {
        System.out.println("deck " + name + " size is: " + size());
        if (size() >= 20) {
            System.out.println("deck is full.");
            return false;
        } else if (getHeroCount() > 0 && card instanceof Hero) {
            System.out.println("can't add more than one hero to deck.");
            return false;
        } else if (getHeroCount() == 0 && !(card instanceof Hero) && size() == 19) {
            System.out.println("can't add more than one hero to deck.");
            return false;
        }
        return super.add(card);
    }

    public boolean isValid() {
        return size() == 20 && getHeroCount() == 1;
    }

    public Deck(String name) {
        this.name = name;
    }

    public Deck(Deck deck) {
        this.name = deck.name;
        for (Map.Entry<Card, Integer> entry : deck.cards.entrySet()) {
            Card card = entry.getKey();
            Integer count = entry.getValue();
            Card newCard = new Card();
            if (card instanceof Spell)
                newCard = new Spell((Spell) card);
            if (card instanceof Minion)
                newCard = new Minion((Minion) card);
            if (card instanceof Hero)
                newCard = new Hero((Hero) card);
            this.cards.put(newCard, entry.getValue());
        }
    }

    public int getHeroCount() {
        return filter(Hero.class, "").size();
    }
}
