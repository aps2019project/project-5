package models.match;

import models.Player;
import models.items.Item;
import models.map.Map;

import java.util.ArrayList;

public class Match {
    private Map map;
    private Player[] players = new Player[2];
    private int turn;
    private boolean multiPlayerMatch;
    private ArrayList<Item> collectibleItems = new ArrayList<>();

    public Player getPlayer1() {
        return players[0];
    }

    public Player getPlayer2() {
        return players[1];
    }

    public void nextTurn() {
        turn++;
        // TODO: Implement
    }

    private Map getMap() {
        return map;
    }

    private Match(Player player1, Player player2) {
        player1 = player1;
        player2 = player2;
    }

    public Player getWinner() {
        return null;
    }

    public int getTurn() {
        return turn;
    }
}