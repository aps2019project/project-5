package models;

import models.map.Map;

public class Match {
    private Map map;
    private Player[] players = new Player[2];
    private int turn;

    public Player getPlayer1() { return players[0]; }
    public Player getPlayer2() { return players[1]; }
    public void nextTern() {}
}