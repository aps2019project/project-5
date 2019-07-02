package models.cards;

public class Deck extends Collection {
    public String name;

    @Override
    public boolean add(Card card) {
        if(size() > 20)
            return false;
        return super.add(card);
    }

    public Deck(String name) {
        this.name = name;
    }
}
