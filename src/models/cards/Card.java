package models.cards;

import models.cards.spell.Buff;
import models.map.Cell;

public class Card {
    private Cell cell;
    private Buff buff;
    private int price;
    private int manaPoint;
    private int id;
    private String description;

    public void moveCard(Cell cell) {}
    public void attack(Cell cell) {}

    public Cell getCell() { return this.cell; }
    public Buff getBuff() { return this.buff; }
    public int getPrice() { return this.price; }
    public int getManaPoint() { return this.manaPoint; }
}
