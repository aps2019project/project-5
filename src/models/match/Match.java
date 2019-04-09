package models.match;

import models.Player;
import models.items.Item;
import models.map.Cell;
import models.map.Map;

import java.util.ArrayList;

public abstract class Match {
    private Map map;
    private Player[] players = new Player[2];
    private int turn;
    private boolean multiPlayerMatch;
    private ArrayList<Item> collectableItems = new ArrayList<>();

    public Player getPlayer1() { return players[0]; }
    public Player getPlayer2() { return players[1]; }
    public void nextTurn() {}
    private Map getMap() { return map; }

    public abstract Player getWinner();
    public int getTurn() { return turn; }
}