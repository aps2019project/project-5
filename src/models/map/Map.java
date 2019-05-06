package models.map;

import controllers.Manager;
import models.Collection;
import models.Player;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.cards.spell.TargetType;
import views.Error;

import java.util.ArrayList;
import java.util.List;

import static models.cards.spell.TargetType.SQUARE3x3;

public class Map {
    public final static int ROW_NUMBER = 5, COLUMN_NUMBER = 9;
    // TODO: 5/5/19 write to cells
    private Cell[][] cells = new Cell[5][9];
    private Collection cards = new Collection();

    public Collection getCards() {
        return this.cards;
    }

    public Map() {
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 9; j++)
                cells[i][j] = new Cell();
    }

    public Cell getCell(int x, int y) throws InvalidCellException {
        if (!cellExist(x, y))
            throw new InvalidCellException(Error.INVALID_CELL.toString());
        return cells[x][y];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String username = null;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 9; j++) {
                if(cells[i][j].getAttacker() == null)
                    stringBuilder.append(" _ ");
                else {
                    if(username == null)
                        username = cells[i][j].getAttacker().getUsername();
                    char cardPrefix;
                    if(cells[i][j].getAttacker().getUsername().equals(username))
                        cardPrefix = '+';
                    else
                        cardPrefix = '-';
                    stringBuilder.append(String.format("%c%02d", cardPrefix, cells[i][j].getAttacker().getId()));
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private static boolean isBetween(int number, int down, int up) {
        return number >= down && number < up;
    }

    public boolean cellExist(int x, int y) {
        return isBetween(x, 0, ROW_NUMBER) && isBetween(y, 0, COLUMN_NUMBER);
    }

    public void insertCard(Card card, Cell cell) throws InvalidCellException, InvalidTargetCellException, Player.HeroDeadException {
        if (cell.isFull())
            throw new InvalidCellException(Error.INVALID_TARGET.toString());
        if (!cards.contains(card)) {
            // TODO: 5/4/19 check if contains
        }
        if (card instanceof Attacker) cell.setAttacker((Attacker) card);
        if (card instanceof Spell) {
            List<Cell> effectedCells = getEffectCells((Spell) card, cell);
            effectedCells.forEach(cell1 -> {
                        cell1.getAttacker().getBuffActivated().addAll(((Spell) card).getBuffs());
                    }
            );

        }

    }

    private List<Cell> getEffectCells(Spell spell, Cell cell) throws InvalidTargetCellException, Player.HeroDeadException {
        TargetType targetType = spell.getTargetType();
        List<Cell> targetCells = new ArrayList<>();
        Attacker attacker = cell.getAttacker();
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
                //TODO: complete squares!
                break;
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
            case ALL_OPPONENT_FORCES:
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
            case ALL_OPPONENT_FORCES_IN_ONE_COLUMN:
                for (int i = 0; i < 5; i++) {
                    if (cells[i][cell.getY()].getAttacker() != null) {
                        targetCells.add(cells[i][cell.getY()]);
                    }
                }
                return targetCells;
        }
        throw new InvalidTargetCellException();
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
            if (opponentPlayer.getActiveCards().contains(cells[cell1.getX() + dx][cell1.getY() + dy].getAttacker()))
                return false;
        }
        return true;

    }

    public static class InvalidTargetCellException extends Exception {
        public InvalidTargetCellException() {
            super("");
        }
    }
}
