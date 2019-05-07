package models.match;

import controllers.Manager;
import models.Account;
import models.Collection;
import models.Player;
import models.cards.*;
import models.cards.buff.Buff;
import models.items.Item;
import models.map.Cell;
import models.map.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class Match {
    private Map map;
    protected Player[] players = new Player[2];
    private int turn = 0;
    private ArrayList<Item> collectibleItems = new ArrayList<>();
    final int PLAYERS_COUNT = 2;
    private boolean isStory;
    private boolean isAIMode;
    private MatchMode matchMode;
    private java.util.Map<String, Card> graveyardCards = new HashMap<>();

    public List<Card> getGraveyardCards() {
        return new ArrayList<>(graveyardCards.values());
    }

    public void addCardToGraveyard(Card card) {
        graveyardCards.put(card.getID(), card);
    }

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

    private void turnPreparing(Player player) {
        List<Buff> disActivated = new ArrayList<>();
        player.getActiveCards().forEach(
                attacker -> {
                    attacker.setCounterAttackAbility(true);
                    attacker.setTurnAttackAvailability(true);
                    attacker.setMoveAbility(true);
                    attacker.getBuffActivated().forEach(
                            buff -> {
                                if (!buff.buffIsActivated()) disActivated.add(buff);
                                else buff.buffEffect(attacker);
                            }
                    );
                    attacker.getBuffActivated().removeAll(disActivated);
                }

        );
    }

    public void setTurn() {
        getActivePlayer().setMana(turn / 2 + 2);
        turnPreparing(players[0]);
        turnPreparing(players[1]);

    }

    public void nextTurn() {
        turn++;
        setTurn();
        // TODO: Implement
    }

    public List<Minion> showMinions(Player player) {
        return player.getActiveCards().stream().filter(
                card -> card instanceof Minion
        ).map(
                (card) -> (Minion) card
        ).collect(Collectors.toList());
    }

    public Map getMap() {
        return map;
    }

    protected Match(Account account1, Account account2) {
        map = new Map();
        players[0] = new Player(account1);
        players[1] = new Player(account2);
        Hero hero1 = players[0].getDeck().getHero();
        Hero hero2 = players[1].getDeck().getHero();
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        try {
            cell1 = map.getCell(2, 0);
            cell2 = map.getCell(2, 8);
        } catch (Map.InvalidCellException ignored) {
        }
        try {
            map.insertCard(hero1, cell1);
            map.insertCard(hero2, cell2);
        } catch (Map.InvalidCellException | Map.InvalidTargetCellException | Player.HeroDeadException ignored) {
        }
        hero1.setCell(cell1);
        hero2.setCell(cell2);
        players[0].getActiveCards().add(hero1);
        players[1].getActiveCards().add(hero2);

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
        return players[(turn + 1) % 2];
    }

    public Player getInActivePlayer() {
        return players[turn % 2];
    }

    public Card getCard(String cardID) throws Collection.CardNotFoundException {
        for (Card card : getActiveCards()) {
            if (card.getID().equals(cardID))
                return card;
        }
        throw new Collection.CardNotFoundException("Card not found");
    }

    private ArrayList<Attacker> getActiveCards() {
        ArrayList<Attacker> activeCards = new ArrayList<>();
        activeCards.addAll(players[0].getActiveCards());
        activeCards.addAll(players[1].getActiveCards());
        return activeCards;
    }

    public List<Minion> showOpponentMinions() {
        return getInActivePlayer().getDeck().getMinions();
    }


    public void moveTo(int x2, int y2) throws InvalidMoveException, Map.InvalidCellException {
        Card card = getActivePlayer().getSelectedCard();
        Cell cell2 = map.getCell(x2, y2);
        if (!map.isValidMove(card, this.getInActivePlayer(), cell2)) throw new InvalidMoveException();
        map.getCell(card.getCell().getX(), card.getCell().getY()).removeCard();
        if (card instanceof Attacker) map.getCell(x2, y2).addCard((Attacker) card);
        card.setCell(cell2);
        ((Attacker) card).setMoveAbility(false);
    }

    public void isValidAttack(Card card, Card opponentCard) throws CardAttackIsNotAvailableException, OpponentMinionIsNotAvailableForAttack, TiredMinionException {
        Player player = getActivePlayer();
        if (!(card instanceof Attacker)) {
            throw new CardAttackIsNotAvailableException(card.getID());
        }
        if (((Attacker) card).getAttackType() == AttackType.RANGED) {
            if (((Attacker) card).getRange() <
                    Cell.manhattanDistance(card.getCell(), opponentCard.getCell()) ||
                    (Math.abs(card.getCell().getX() - opponentCard.getCell().getX()) <= 1 &&
                            Math.abs(card.getCell().getY() - opponentCard.getCell().getY()) <= 1)) {
                throw new OpponentMinionIsNotAvailableForAttack();
            }
        }

        if (((Attacker) card).getAttackType() == AttackType.MELEE) {
            if (Math.abs(card.getCell().getX() - opponentCard.getCell().getX()) > 1 &&
                    Math.abs(card.getCell().getY() - opponentCard.getCell().getY()) > 1)
                throw new OpponentMinionIsNotAvailableForAttack();
        }

        if (!((Attacker) card).getTurnAttackAvailability()) throw new TiredMinionException(card.getID());
    }

    public void attack(String cardID) throws CardAttackIsNotAvailableException,
            TiredMinionException, OpponentMinionIsNotAvailableForAttack, Collection.CardNotFoundException {
        Card card = getActivePlayer().getSelectedCard();
        Card opponentCard = getInActivePlayer().getActiveCard(cardID);
        isValidAttack(card, opponentCard);
        ((Attacker) card).setMoveAbility(false);
        ((Attacker) opponentCard).decrementCurrentHealth(((Attacker) card).getAttackPoint());
        try {
            isValidAttack(opponentCard, card);
            ((Attacker) card).decrementCurrentHealth(((Attacker) opponentCard).getAttackPoint());
        } catch (Exception e) {

        }

    }

    public Card getGraveyardCard(String cardID) {
        return graveyardCards.get(cardID);
    }

    public static class InvalidMoveException extends Exception {
        public InvalidMoveException() {
            super("Invalid Move!");
        }
    }

    public class CardAttackIsNotAvailableException extends Exception {
        private String id;

        public CardAttackIsNotAvailableException(String id) {
            super("Invalid attack");
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public class OpponentMinionIsNotAvailableForAttack extends Exception {
        public OpponentMinionIsNotAvailableForAttack() {
            super("opponentMinionIsNotAvailable!");
        }
    }


    public class TiredMinionException extends Exception {
        String id;

        public TiredMinionException(String id) {
            super("Minion is Tired!");
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}