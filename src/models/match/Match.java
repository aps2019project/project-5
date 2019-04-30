package models.match;

import models.Account;
import models.Player;
import models.cards.Card;
import models.items.Item;
import models.map.Map;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Match {
    private Map map;
    protected Player[] players = new Player[2];
    private int turn;
    private ArrayList<Item> collectibleItems = new ArrayList<>();
    final int PLAYERS_COUNT = 2;
    private boolean isStory;
    private boolean isAIMode;

    public void setAIMode(boolean AIMode) {
        isAIMode = AIMode;
    }

    public boolean isAIMode() {
        return isAIMode;
    }

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

    public Player getActivePlayer() {
        return players[ (turn + 1) % 2];
    }

    public Player getInActivePlayer() {
        return players[ turn % 2];
    }

    public Card getCard(int cardID) {
        getActiveCards()
                .stream()
                .filter(
                        card -> card.getID() == cardID)
                .collect(Collectors.toList())
                .get(0);
    }

    private List<Card> getActiveCards() {
        List<Card> allActiveCards = new ArrayList<>(players[0].getActiveCards());
        allActiveCards.addAll(players[1].getActiveCards());
        return allActiveCards;
    }
}