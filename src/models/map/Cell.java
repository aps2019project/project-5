package models.map;

import models.cards.Card;
import models.items.Item;

public class Cell {
    private int x, y;
    private Item item;
    private Card card;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Item getItem() {
        return item;
    }

    public Card getCard() {
        return card;
    }

    public boolean isFull() {
        return card != null;
    }

    public static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static int manhattanDistance(Cell cell1, Cell cell2) {
        return manhattanDistance(cell1.x, cell1.y, cell2.x, cell2.y);
    }

    public void removeCard() {
        this.card = null;
    }

    public void addCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + " )";
    }
}
