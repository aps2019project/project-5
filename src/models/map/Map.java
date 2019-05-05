package models.map;

import models.Player;
import models.cards.Card;

public class Map {
    private Cell[][] cells = new Cell[5][9];

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public boolean isValidMove(Card card, Player opponentPlayer, Cell cell2) {
        Cell cell1 = card.getCell();
        int maxDistance = card.getMaxDistance();
        int distance = Cell.manhattanDistance(cell1, cell2);
        if (distance > card.getMaxDistance()) return false;
        if (cell2.getCard() != null) return false;
        if (!card.isMoveAvailable()) return false;
        int dx = 0;
        int dy = 0;
        if (distance >= 2) {
            if (cell1.getX() > cell2.getX()) dx = -1;
            if (cell1.getX() < cell2.getX()) dx = 1;
            if (cell1.getY() > cell2.getY()) dy = -1;
            if (cell1.getY() < cell2.getY()) dx = 1;
            if (opponentPlayer.getActiveCards().getCardsList().contains(cells[cell1.getX() + dx][cell1.getY() + dy].getCard()))
                return false;
        }
        return true;

    }

}
