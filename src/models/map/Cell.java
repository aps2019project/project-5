package models.map;

import models.cards.Attacker;
import models.cards.Card;
import models.items.Flag;
import models.items.Item;

public class Cell {
    private int x, y;
    private Item item;
    private Attacker attacker;
    private Flag flag;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell() {}

    public void setAttacker(Attacker attacker) {
        this.attacker = attacker;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Item getItem() {
        return item;
    }

    public Attacker getAttacker() {
        return attacker;
    }

    public boolean isFull() {
        return attacker != null;
    }

    public static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static int manhattanDistance(Cell cell1, Cell cell2) {
        return manhattanDistance(cell1.x, cell1.y, cell2.x, cell2.y);
    }

    public void removeCard() {
        this.attacker = null;
    }

    public void addCard(Attacker attacker) {
        this.attacker = attacker;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public boolean hasFlag() {
        return flag != null;
    }


}
