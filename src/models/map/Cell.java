package models.map;

import models.cards.Attacker;

public class Cell {
    public int x, y;
    public Attacker attacker;
    public boolean hasFlag = false;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static int getManhattanDistance(Cell firstCell, Cell secondCell) {
        return Math.abs(firstCell.x - secondCell.x) + Math.abs(secondCell.y - firstCell.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
