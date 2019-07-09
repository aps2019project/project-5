package models.match.action;

import models.cards.Card;
import models.map.Cell;

public class Move extends GameAction {
    public Card card;
    public Cell previousCell;
    public Cell newCell;

    public Move(Card card, Cell previousCell, Cell newCell) {
        this.card = card;
        this.previousCell = previousCell;
        this.newCell = newCell;
    }
}
