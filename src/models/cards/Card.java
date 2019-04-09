package models.cards;

import models.cards.spell.Buff;
import models.map.Cell;

public class Card {
    private Cell cell;
    private Buff buff;
    private int price;
    private int manaPoint;
    private String description;

    public void moveCard(Cell cell) {}
    public void attack(Cell cell) {}
}
