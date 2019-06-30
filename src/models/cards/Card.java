package models.cards;

public class Card {
    public String name, description;
    public int price, manaPoint;

    public Card(String name, String description, int price, int manaPoint) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.manaPoint = manaPoint;
    }

    public Card() {

    }

    @Override public String toString() {
        return name;
    }
}
