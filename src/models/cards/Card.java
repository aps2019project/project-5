package models.cards;

import models.MarketObject;
import models.cards.spell.Buff;
import models.map.Cell;

public class Card extends MarketObject {
    private Cell cell;
    private Buff buff;
    private int price;
    private int manaPoint;
    private int id;
    private String description;

    public Card() {}

    public void moveCard(Cell cell) {
    }

    public void attack(Cell cell) {
    }

    public Cell getCell() {
        return this.cell;
    }

    public Buff getBuff() {
        return this.buff;
    }

    public int getManaPoint() {
        return this.manaPoint;
    }


    // this constructor can be called only in hero, minion and usable item class
    protected Card(String name, String description, int manaPoint, int price) {
        this.name = name;
        this.description = description;
        this.manaPoint = manaPoint;
        this.price = price;
    }
}
