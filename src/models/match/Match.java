package models.match;

import models.Account;
import models.Player;
import models.items.Item;
import models.map.Map;

import javax.print.DocFlavor;
import java.util.ArrayList;

public abstract class Match {
    private Map map;
    protected Player[] players = new Player[2];
    private int turn;
    private boolean AIMode;
    private ArrayList<Item> collectibleItems = new ArrayList<>();
    final int PLAYERS_COUNT = 2;
    private boolean isStory;

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

    public void setAIMode(boolean AIMode) {
        this.AIMode = AIMode;
    }

    private Map getMap() {
        return map;
    }

    protected Match(Player player1, Player player2) {
        player1 = player1;
        player2 = player2;
    }

    abstract public Player getWinner() ;

    public Player[] getPlayers() {
        return players;
    }

    public int getTurn() {
        return turn;
    }

    abstract public String getInfo();

    public void setState(boolean isStory) {
        this.isStory = isStory;
    }
}