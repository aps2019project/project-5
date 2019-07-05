package models.match;

import models.Account;
import models.cards.*;
import models.map.Cell;
import models.map.Map;

import java.util.HashSet;
import java.util.Set;

public class Match {
    public String token;
    public Map map = new Map();
    public Player[] players = new Player[2];
    public int turn = 0;

    public Player getActivePlayer() {
        return players[turn % 2];
    }

    public Player getInActivePlayer() {
        return players[1 - turn % 2];
    }

    private void setMana() {
        if (turn / 2 + 2 >= 9) getActivePlayer().manaPoint = 9;
        else getActivePlayer().manaPoint = turn / 2 + 2;
    }

    public void endTurn() {
        getActivePlayer().selectedCard = null;
        turn++;
        setMana();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++)
                try {
                    map.cell[i][j].attacker.canMove = true;
                } catch (Throwable ignored) {
                }
    }

    public boolean insertCard(int x, int y) {
        Card selectedCard = getActivePlayer().selectedCard;
        if (selectedCard != null) {
            if (!selectedCard.isInserted &&
                    getActivePlayer().manaPoint >= selectedCard.manaPoint &&
                    map.cell[x][y].attacker == null &&
                    getAvailableCells().contains(map.cell[x][y])) {
                map.cell[x][y].attacker = (Attacker) selectedCard;
                selectedCard.isInserted = true;
                ((Attacker) selectedCard).cell = map.cell[x][y];
                getActivePlayer().manaPoint -= selectedCard.manaPoint;
                getActivePlayer().selectedCard = null;
                return true;
            }
        }
        return false;
    }

    public Set<Cell> getAvailableCells() {
        Card card = getActivePlayer().selectedCard;
        Set<Cell> availableCells = new HashSet<>();
        if (card.isInserted) {
            if (card instanceof Attacker) {
                Attacker attacker = (Attacker) card;
                int x = attacker.cell.x;
                int y = attacker.cell.y;
                for (int di = -1; di <= 1; di++)
                    for (int dj = -1; dj <= 1; dj++)
                        try {
                            if (map.cell[x + di][y + dj].attacker == null)
                                availableCells.add(map.cell[x + di][y + dj]);
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                try {
                    if (map.cell[x - 1][y].attacker == null) {
                        if (map.cell[x - 2][y].attacker == null)
                            availableCells.add(map.cell[x - 2][y]);
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (map.cell[x + 1][y].attacker == null) {
                        if (map.cell[x + 2][y].attacker == null)
                            availableCells.add(map.cell[x + 2][y]);
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (map.cell[x][y - 1].attacker == null) {
                        if (map.cell[x][y - 2].attacker == null)
                            availableCells.add(map.cell[x][y - 2]);
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (map.cell[x][y + 1].attacker == null) {
                        if (map.cell[x][y + 2].attacker == null)
                            availableCells.add(map.cell[x][y + 2]);
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            } else if (card instanceof Spell) {
                availableCells.addAll(map.getTarget(((Spell) card).targetType, getActivePlayer()));
            }
        } else {
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 9; j++)
                    if (map.cell[i][j].attacker != null)
                        if (map.cell[i][j].attacker.playerName.equals(getActivePlayer().account.username))
                            for (int di = -1; di <= 1; di++)
                                for (int dj = -1; dj <= 1; dj++)
                                    try {
                                        if (map.cell[i + di][j + dj].attacker == null)
                                            availableCells.add(map.cell[i][j]);
                                    } catch (ArrayIndexOutOfBoundsException ignored) {
                                    }
        }
        return availableCells;
    }

    public boolean moveCard(int x, int y) {
        Cell cell;
        try {
            cell = map.cell[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        Card selectedCard = getActivePlayer().selectedCard;
        if (selectedCard != null &&
                selectedCard.canMove &&
                selectedCard.isInserted &&
                getAvailableCells().contains(cell)) {
            ((Attacker) selectedCard).cell.attacker = null;
            cell.attacker = (Attacker) selectedCard;
            ((Attacker) selectedCard).cell = cell;
            selectedCard.canMove = false;
            return true;
        }
        return false;
    }

    public boolean isValidAttack(Cell cell, Attacker attacker) {
//        Attacker attacker = (Attacker) getActivePlayer().selectedCard;
        if (attacker.attackType == AttackType.HYBRID) {
            return true;
        }
        int distance = Cell.getManhattanDistance(cell, attacker.cell);
        if (attacker.attackType == AttackType.RANGED) {
            if (distance <= attacker.attackRange && distance > 1) {
                return true;
            }
        }
        if (attacker.attackType == AttackType.RANGED) {
            return distance == 1;
        }
        return false;
    }

    public int attack(int x, int y) {
        Cell cell;
        try {
            cell = map.cell[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
        Attacker attacker = (Attacker) getActivePlayer().selectedCard;
        Attacker targetCard = cell.attacker;
        if (attacker != null &&
                targetCard != null &&
                attacker.canMove &&
                attacker.isInserted &&
                getAvailableCells().contains(cell)) {
            if (isValidAttack(cell, (Attacker) getActivePlayer().selectedCard)) {
                targetCard.health -= attacker.getAttackPoint();
                if (isValidAttack(attacker.cell, targetCard)) {
                    attacker.health -= targetCard.getAttackPoint();
                    return 2;
                }
                return 1;
            }
        }
        return -1;
    }

    public boolean selectCard(int id) {
        for (int i = 0; i < 5; i++) {
            try {
                if (getActivePlayer().hand.get(i).id == id) {
                    getActivePlayer().selectedCard = getActivePlayer().hand.get(i);
                    return true;
                }
            } catch (Throwable ignored) {
            }
        }
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                if (map.cell[i][j].attacker == null)
                    continue;
                if (map.cell[i][j].attacker.id == id &&
                        map.cell[i][j].attacker.playerName.equals(
                                getActivePlayer().account.username
                        )) {
                    getActivePlayer().selectedCard = map.cell[i][j].attacker;
                    return true;
                }
            }
        return false;
    }

    private void putHeroes() {
        Hero hero1 = new Hero();
        Hero hero2 = new Hero();
        for (Card card : players[0].hand)
            if (card instanceof Hero)
                hero1 = (Hero) card;

        for (Card card : players[1].hand)
            if (card instanceof Hero)
                hero2 = (Hero) card;

        players[0].hand.remove(hero1);
        players[1].hand.remove(hero2);

        Cell cell1 = map.cell[2][0];
        Cell cell2 = map.cell[2][8];

        hero1.cell = cell1;
        hero2.cell = cell2;

        hero1.canMove = true;
        hero2.canMove = true;

        hero1.isInserted = true;
        hero2.isInserted = true;

        cell1.attacker = hero1;
        cell2.attacker = hero2;
    }

    public Match(Account account1, Account account2) {
        players[0] = new Player(account1);
        players[1] = new Player(account2);
        putHeroes();
    }
}
