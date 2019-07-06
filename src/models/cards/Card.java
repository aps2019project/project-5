package models.cards;

import java.util.Objects;

public class Card {
    public String name;
    public String description;
    public String playerName;
    public int id;
    public int price;
    public int manaPoint;
    public boolean isInserted = false;
    public boolean canMove;

    public Card(String name, String description, int price, int manaPoint) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.manaPoint = manaPoint;
    }

    public Card(Card card) {
        this.name = card.name;
        this.description = card.description;
        this.price = card.price;
        this.manaPoint = card.manaPoint;
    }

    public Card() {

    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(name, card.name);
    }

    public boolean equalsInGame(Card card) {
        if(card == null)
            return false;
        if(card.playerName == null || this.playerName == null)
            return false;
        else
            return card.playerName.equals(this.playerName) && this.id == card.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
