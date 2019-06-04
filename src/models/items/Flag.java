package models.items;

import models.cards.Card;
import models.map.Cell;

public class Flag extends Item {
    int tokenTurn;
    Card owner;

    public Flag(String name, Cell cell) {
        super(name, cell, "");
        tokenTurn = -1;
    }

    public Flag() {
        super();
    }

    public int getTokenTurn() {
        return tokenTurn;
    }

    public boolean isToken() {
        return owner != null;
    }

    public void release() {
        this.tokenTurn = -1;
        this.owner = null;
    }

    public void take(int turn, Card card) {
        this.owner = card;
        this.tokenTurn = turn;
    }

    public Card getOwner() {
        return owner;
    }
}
