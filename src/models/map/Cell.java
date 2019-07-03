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
}
