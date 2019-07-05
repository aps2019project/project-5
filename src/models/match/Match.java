package models.match;

import models.Account;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.map.Cell;
import models.map.Map;

public class Match {
    public String token;
    public Map map = new Map();
    public Player[] players = new Player[2];
    public int turn = 0;

    public Player getActivePlayer() {
        return players[turn % 2];
    }

    public Player getInActivePlayer() {
        return players[1 - turn % 2];
    }

    private void setMana() {
        getActivePlayer().manaPoint = turn / 2 + 2;
    }

    public void endTurn() {
        getActivePlayer().selectedCard = null;
        turn++;
        setMana();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++)
                try {
                    map.cell[i][j].attacker.canMove = true;
                } catch (Throwable ignored) {}
    }

    public boolean insertCard(int x, int y) {
        Card selectedCard = getActivePlayer().selectedCard;
        if (selectedCard != null) {
            if (!selectedCard.isInserted &&
                    getActivePlayer().manaPoint >= selectedCard.manaPoint &&
                    map.cell[x][y].attacker == null) {
                map.cell[x][y].attacker = (Attacker) selectedCard;
                selectedCard.isInserted = true;
                ((Attacker) selectedCard).cell = map.cell[x][y];
                getActivePlayer().manaPoint -= selectedCard.manaPoint;
                getActivePlayer().selectedCard = null;
                return true;
            }
        }
        return false;
    }

    public boolean selectCard(int id) {
        for (int i = 0; i < 5; i++) {
            try {
                if (getActivePlayer().hand.get(i).id == id) {
                    getActivePlayer().selectedCard = getActivePlayer().hand.get(i);
                    return true;
                }
            } catch (Throwable ignored) {
            }
        }
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                if (map.cell[i][j].attacker == null)
                    continue;
                if (map.cell[i][j].attacker.id == id &&
                        map.cell[i][j].attacker.playerName.equals(
                                getActivePlayer().account.username
                        )) {
                    getActivePlayer().selectedCard = map.cell[i][j].attacker;
                    return true;
                }
            }
        return false;
    }

    private void putHeroes() {
        Hero hero1 = new Hero();
        Hero hero2 = new Hero();
        for(Card card : players[0].hand)
            if(card instanceof Hero)
                hero1 = (Hero) card;

        for(Card card : players[1].hand)
            if(card instanceof Hero)
                hero2 = (Hero) card;

        players[0].hand.remove(hero1);
        players[1].hand.remove(hero2);

        Cell cell1 = map.cell[2][0];
        Cell cell2 = map.cell[2][8];

        hero1.cell = cell1;
        hero2.cell = cell2;

        hero1.canMove = true;
        hero2.canMove = true;

        hero1.isInserted = true;
        hero2.isInserted = true;

        cell1.attacker = hero1;
        cell2.attacker = hero2;
    }

    public Match(Account account1, Account account2) {
        players[0] = new Player(account1);
        players[1] = new Player(account2);
        putHeroes();
    }
}
