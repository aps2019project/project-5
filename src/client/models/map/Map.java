package client.models.map;

import client.controllers.Manager;
import client.models.Collection;
import client.models.Player;
import client.models.cards.Attacker;
import client.models.cards.Card;
import client.models.cards.Minion;
import client.models.cards.spell.Spell;
import client.models.cards.spell.TargetType;
import client.models.items.Flag;
import client.views.Error;

import java.util.ArrayList;
import java.util.List;

public class Map {
    public final static int ROW_NUMBER = 5, COLUMN_NUMBER = 9;
    private Cell[][] cells = new Cell[5][9];
    private Collection cards = new Collection();

    public List<Cell> getCellsDistance(Cell cell, int distance) {
        int x = cell.getX(), y = cell.getY();
        List<Cell> cells = new ArrayList<>();
        for (int i = -distance; i <= distance; i++) {
            final int remainedDistance = distance - Math.abs(i);
            for (int j = -remainedDistance; j <= remainedDistance; j++) {
                try {
                    cells.add(this.getCell(x + i, y + j));
                } catch (InvalidCellException ignored) {
                }
            }
        }
        return cells;
    }

    public Collection getCards() {
        return this.cards;
    }

    public Map() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++)
                cells[i][j] = new Cell(i, j);
    }

    public Cell getCell(int x, int y) throws InvalidCellException {
        if (!cellExist(x, y))
            throw new InvalidCellException(Error.INVALID_CELL.toString());
        return cells[x][y];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String username1 = null;
        String username2 = null;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].getAttacker() != null) {
                    if (username1 == null)
                        username1 = cells[i][j].getAttacker().getUsername();
                    if (username2 == null && username1 != null && !cells[i][j].getAttacker().getUsername().equals(username1))
                        username2 = cells[i][j].getAttacker().getUsername();
                    char cardPrefix;
                    if (cells[i][j].getAttacker().getUsername().equals(username1))
                        cardPrefix = '+';
                    else
                        cardPrefix = '-';
                    stringBuilder.append(String.format("%c%02d", cardPrefix, cells[i][j].getAttacker().getId()));
                } else if (cells[i][j].hasFlag()) {
                    stringBuilder.append("F");
                } else
                    stringBuilder.append(" _ ");

            }
            stringBuilder.append("\n");
        }
        stringBuilder.append(String.format("+: %s\n-: %s", username1, username2));
        return stringBuilder.toString();
    }

    private static boolean isBetween(int number, int down, int up) {
        return number >= down && number < up;
    }

    public boolean cellExist(int x, int y) {
        return isBetween(x, 0, ROW_NUMBER) && isBetween(y, 0, COLUMN_NUMBER);
    }

    public void insertCard(Card card, Cell cell) throws InvalidCellException, InvalidTargetCellException, Player.HeroDeadException {
        if (cell.isFull() && (card instanceof Minion))
            throw new InvalidCellException(Error.INVALID_TARGET.toString());
        if (!cards.contains(card)) {
            // TODO: 5/4/19 check if contains
        }
        if (card instanceof Attacker) cell.setAttacker((Attacker) card);
        if (card instanceof Spell) {
            List<Cell> effectedCells = getEffectCells((Spell) card, cell);
//            effectedCells.forEach(cell1
//                    -> cell1.getAttacker().getBuffActivated().addAll(((Spell) card).getBuffs())
//            );

        }

    }

    private List<Cell> getEffectCells(Spell spell, Cell cell) throws InvalidTargetCellException, Player.HeroDeadException {
        TargetType targetType = spell.getTargetType();
        List<Cell> targetCells = new ArrayList<>();
        Attacker attacker = cell.getAttacker();
        System.out.println(targetType);
        if(targetType == null)
            return getCells();
        switch (targetType) {
            case MY_HERO:
                if (attacker == Manager.getActivePlayer().getHero()) {
                    targetCells.add(cell);
                    return targetCells;
                }
                break;
            case MY_FORCE:
                if (Manager.getActivePlayer().getActiveCards().contains(attacker)) {
                    targetCells.add(cell);
                    return targetCells;
                }
                break;
            case MY_MINION:
                if (Manager.getActivePlayer().getActiveCards().contains(attacker) && attacker instanceof Minion) {
                    targetCells.add(cell);
                    return targetCells;
                }
                break;
            case SQUARE2x2:
                return getCells();
            case SQUARE3x3:
                break;
            case ALL_MY_FORCE:
                Manager.getActivePlayer().getActiveCards().forEach(
                        card -> targetCells.add(card.getCell())
                );
                return targetCells;
            case AN_OPPONENT_FORCE:
                if (Manager.getInActivePlayer().getActiveCards().contains(attacker)) {
                    targetCells.add(cell);
                    return targetCells;
                }
                return targetCells;
            case AN_OPPONENT_MINION:
                if (Manager.getInActivePlayer().getActiveCards().contains(attacker) && attacker instanceof Minion) {
                    targetCells.add(cell);
                    return targetCells;
                }
                break;
            case ALL_OPPONENT_FORCE:
                Manager.getInActivePlayer().getActiveCards().forEach(
                        card -> targetCells.add(card.getCell())
                );
                return targetCells;
            case OPPONENT_FORCE_OR_MY_FORCE:
                if (Manager.getActivePlayer().getActiveCards().contains(attacker) ||
                        Manager.getInActivePlayer().getActiveCards().contains(attacker)) {
                    targetCells.add(cell);
                    return targetCells;
                }
                return targetCells;
            case ALL_OPPONENT_FORCE_IN_ONE_COLUMN:
                for (int i = 0; i < 5; i++) {
                    if (cells[i][cell.getY()].getAttacker() != null) {
                        targetCells.add(cells[i][cell.getY()]);
                    }
                }
                return targetCells;
            case OPPONENT_HERO:
        }

        throw new InvalidTargetCellException();
    }

    public void setFlag(int x, int y) {
        cells[x][y].setFlag(new Flag());
    }

    public Cell getLeft(Cell cell) throws InvalidCellException {
        int x = cell.getX(), y = cell.getY() - 1;
        return getCell(x, y);
    }

    public Cell getTop(Cell cell) throws InvalidCellException {
        int x = cell.getX() - 1, y = cell.getY();
        return getCell(x, y);
    }

    public Cell getBottom(Cell cell) throws InvalidCellException {
        int x = cell.getX() + 1, y = cell.getY();
        return getCell(x, y);
    }

    public Cell getRight(Cell cell) throws InvalidCellException {
        int x = cell.getX(), y = cell.getY() + 1;
        return getCell(x, y);
    }

    public List<Cell> getNeighbors(Cell cell) {
        List<Cell> cells = new ArrayList<>();
        try {
            final Cell right = this.getRight(cell);
            cells.add(right);
            cells.add(getTop(right));
        } catch (InvalidCellException e) {
        }
        try {
            final Cell left = this.getLeft(cell);
            cells.add(left);
            cells.add(getBottom(left));
        } catch (InvalidCellException e) {
        }
        try {
            final Cell top = this.getTop(cell);
            cells.add(top);
            cells.add(getLeft(top));
        } catch (InvalidCellException e) {
        }
        try {
            final Cell bottom = this.getBottom(cell);
            cells.add(bottom);
            cells.add(getRight(bottom));
        } catch (InvalidCellException e) {
        }
        return cells;
    }

    public List<Cell> getCells() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < ROW_NUMBER; i++) {
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                cells.add(this.cells[i][j]);
            }
        }
        return cells;
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
        if (distance > maxDistance) return false;
        if (cell2.getAttacker() != null) return false;
        if (!((Attacker) card).getMoveAbility()) return false;
        int dx = 0;
        int dy = 0;
        if (distance >= 2) {
            if (cell1.getX() > cell2.getX()) dx = -1;
            if (cell1.getX() < cell2.getX()) dx = 1;
            if (cell1.getY() > cell2.getY()) dy = -1;
            if (cell1.getY() < cell2.getY()) dx = 1;
            return !opponentPlayer.getActiveCards().contains(cells[cell1.getX() + dx][cell1.getY() + dy].getAttacker());
        }
        return true;

    }

    public static class InvalidTargetCellException extends Exception {
        public InvalidTargetCellException() {
            super("");
        }
    }
}
