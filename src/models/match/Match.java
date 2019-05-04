package models.match;

import models.Collection;
import models.Player;
import models.cards.Card;
import models.cards.Minion;
import models.items.Item;
import models.map.Map;
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
    private MatchMode matchMode;

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

    public List<Minion> showMyMinions() {
        Player player = this.getActivePlayer();
        return player.getDeck().getMinions();
    }

    private Map getMap() {
        return map;
    }

    protected Match() {

    }

    abstract public Player getWinner();

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

    public Card getCard(int cardID) throws Collection.CardNotFoundException {
        return getActiveCards().getCard(cardID);
    }

    private Collection getActiveCards() {
        Collection allActiveCards = new Collection(players[0].getActiveCards().getCardsList());
        allActiveCards.addCards(players[1].getActiveCards().getCardsList());
        return allActiveCards;
    }

    public List<Minion> showOpponentMinions() {
        return getInActivePlayer().getDeck().getMinions();
    }
}