package models.match.action;

import client.models.cards.Card;
import client.models.map.Cell;

public class Move extends Action {
    public Card card;
    public Cell previousCell;
    public Cell newCell;
}
