package models.match;

import models.Account;
import models.Collection;
import models.Player;
import models.cards.AttackType;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Minion;
import models.items.Item;
import models.map.Cell;
import models.map.Map;

import java.util.ArrayList;
import java.util.List;

public abstract class Match {
    private Map map;
    protected Player[] players = new Player[2];
    private int turn = 0;
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

    public void setTurn() {
        players[0].getActiveCards().getCardsList().forEach(attacker -> {
            ((Attacker) attacker).getBuffActivated().forEach(buff -> buff.buffEffect(attacker));
        });
        players[1].getActiveCards().getCardsList().forEach(
                attacker -> {
                    ((Attacker) attacker).getBuffActivated().forEach(
                            buff -> buff.buffEffect(attacker)
                    );
                }
        );
    }

    public void nextTurn() {
        turn++;

        // TODO: Implement
    }

    public List<Minion> showMyMinions() {
        Player player = this.getActivePlayer();
        return player.getDeck().getMinions();
    }

    public Map getMap() {
        return map;
    }

    protected Match(Account account1, Account account2) {
        map = new Map();
        Player player1 = new Player(account1);
        Player player2 = new Player(account2);

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
        return getActiveCards().getCardByID(cardID);
    }

    private Collection getActiveCards() {
        Collection allActiveCards = new Collection(players[0].getActiveCards().getCardsMap());
        allActiveCards.addCards(players[1].getActiveCards().getCardsMap());
        return allActiveCards;
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
        card.setMoveAvailable(false);
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

    public void attack(String cardID) throws Collection.CardNotFoundException, CardAttackIsNotAvailableException,
            TiredMinionException, OpponentMinionIsNotAvailableForAttack {
        Card card = getActivePlayer().getSelectedCard();
        Card opponentCard = getInActivePlayer().getActiveCards().getCardByID(cardID);
        isValidAttack(card, opponentCard);
        card.setMoveAvailable(false);
        ((Attacker) opponentCard).decrementCurrentHealth(((Attacker) card).getAttackPoint());
        try {
            isValidAttack(opponentCard, card);
            ((Attacker) card).decrementCurrentHealth(((Attacker) opponentCard).getAttackPoint());
        } catch (Exception e) {

        }

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