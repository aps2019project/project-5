package models.map;

import models.cards.Minion;
import models.cards.TargetType;
import models.match.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Map {
    public Cell[][] cell = new Cell[5][9];

    public Map() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                cell[i][j] = new Cell(i, j);
            }
        }
    }

    public Set<Cell> getCellSet() {
        Set<Cell> cells = new HashSet<>();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++)
                cells.add(cell[i][j]);
        return cells;
    }

    public Set<Cell> getTarget(TargetType targetType, Player player) {
        String userName = player.account.username;
        switch (targetType) {
            case MY_FORCE:
            case ALL_MY_FORCE:
                return getCellSet().stream().filter(c -> {
                    if (c.attacker == null)
                        return false;
                    return c.attacker.playerName.equals(userName);
                }).collect(Collectors.toSet());
            case MY_MINION:
                return getCellSet().stream().filter(c -> {
                    if (c.attacker == null)
                        return false;
                    return c.attacker.playerName.equals(userName) && c.attacker instanceof Minion;
                }).collect(Collectors.toSet());
            case ANY:
                return getCellSet();
        }
        return getCellSet();
    }
}
