package models.map;

import models.Collection;
import models.Player;
import models.cards.Attacker;
import models.cards.Card;
import views.Error;

public class Map {
    public final static int ROW_NUMBER = 5, COLUMN_NUMBER = 9;
    // TODO: 5/5/19 write to cells
    private Cell[][] cells = new Cell[5][9];
    private Collection cards = new Collection();

    public Collection getCards() {
        return this.cards;
    }


    public Cell getCell(int x, int y) throws InvalidCellException {
        if (!cellExist(x, y))
            throw new InvalidCellException(Error.INVALID_CELL.toString());
        return cells[x][y];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 9; j++) {
//                if(cells[i][j].getCard())
            }
        }
        return stringBuilder.toString();
    }

    private boolean isBetween(int number, int down, int up) {
        return number >= down && number < up;
    }

    public boolean cellExist(int x, int y) {
        return isBetween(x, 0, ROW_NUMBER) && isBetween(y, 0, COLUMN_NUMBER);
    }

    public void insertCard(Card card, Cell cell) throws InvalidCellException, Collection.CollectionException {
        if (cell.isFull())
            throw new InvalidCellException(Error.INVALID_TARGET.toString());
        if (!cards.contains(card)) {
            // TODO: 5/4/19 check if contains
        }
       if (card instanceof Attacker) cell.setAttacker((Attacker) card);
        cards.addCard(card);
    }

    public class InvalidCellException extends Exception {
        public InvalidCellException(String message) {
            super(message);
        }
    }

    public boolean isValidMove(Card card, Player opponentPlayer, Cell cell2) {
        Cell cell1 = card.getCell();
        int maxDistance = card.getMaxDistance();
        int distance = Cell.manhattanDistance(cell1, cell2);
        if (distance > card.getMaxDistance()) return false;
        if (cell2.getAttacker() != null) return false;
        if (!card.isMoveAvailable()) return false;
        int dx = 0;
        int dy = 0;
        if (distance >= 2) {
            if (cell1.getX() > cell2.getX()) dx = -1;
            if (cell1.getX() < cell2.getX()) dx = 1;
            if (cell1.getY() > cell2.getY()) dy = -1;
            if (cell1.getY() < cell2.getY()) dx = 1;
            if (opponentPlayer.getActiveCards().getCardsList().contains(cells[cell1.getX() + dx][cell1.getY() + dy].getAttacker()))
                return false;
        }
        return true;

    }

}
