package models.Match;

import models.Player;
import models.map.Map;

public class Match {
    private Map map;
    private Player[] players = new Player[2];
    private int turn;
    private boolean multiPlayerMatch;

    public Player getPlayer1() { return players[0]; }
    public Player getPlayer2() { return players[1]; }
    public void nextTern() {}
}