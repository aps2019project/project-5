package models.match.action;

import models.cards.Card;
import models.map.Cell;

public class Insert extends Action {
    public Card card;
    public Cell cell;

    public Insert(Card card, Cell cell) {
        this.card = card;
        this.cell = cell;
    }
}
