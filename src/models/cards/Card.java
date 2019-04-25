package models.cards;

import models.Collection;
import models.Marketable;
import models.cards.spell.Buff;
import models.map.Cell;

public class Card implements Marketable {
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

    @Override
    public String getDescription() {
        return this.description;
    }

    public Buff getBuff() {
        return this.buff;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    public int getManaPoint() {
        return this.manaPoint;
    }

    @Override
    public String getName() {
        return this.name;
    }

    // this constructor can be called only in hero, minion and usable item class
    protected Card(String name, String description, int manaPoint, int price) {
        this.name = name;
        this.description = description;
        this.manaPoint = manaPoint;
        this.price = price;
    }
}
