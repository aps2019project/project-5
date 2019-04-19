package models.cards;

import models.cards.spell.Buff;
import models.map.Cell;

public class Card {
    private Cell cell;
    private Buff buff;
    private int price;
    private int manaPoint;
    private int id;
    private String name;
    private String description;

    public void moveCard(Cell cell) {
    }

    public void attack(Cell cell) {
    }

    public Cell getCell() {
        return this.cell;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Buff getBuff() {
        return this.buff;
    }

    public int getPrice() {
        return this.price;
    }

    public int getManaPoint() {
        return this.manaPoint;
    }

    public String getName() {
        return this.name;
    }

    // this constructor can be called only by hero, minion and usable item class
    protected Card(int id, String name, String description, int manaPoint, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manaPoint = manaPoint;
        this.price = price;
    }
}
