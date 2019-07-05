package models.match;

import models.Account;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.map.Cell;
import models.map.Map;

public class Match {
    public Map map = new Map();
    public String token;
    public Player[] players = new Player[2];
    public int turn = 0;

    public Player getActivePlayer() {
        return players[turn % 2];
    }

    private void setMana() {
        players[turn % 2].manaPoint = turn / 2 + 2;
    }

    public void endTurn() {
        turn++;
        setMana();

    }

    public void insertCard(int x, int y) {
        Card selectedCard = getActivePlayer().selectedCard;
        if (selectedCard != null) {
            if (!selectedCard.isInserted && getActivePlayer().manaPoint >= selectedCard.manaPoint) {
                map.cell[x][y].attacker = (Attacker) selectedCard;
                selectedCard.isInserted = true;
                ((Attacker) selectedCard).cell = map.cell[x][y];
                getActivePlayer().manaPoint -= selectedCard.manaPoint;
            }
        }
    }

    private void putHeroes() {
        Hero hero1 = (Hero) players[0].deck.filter(Hero.class, "").keySet().toArray()[0];
        Hero hero2 = (Hero) players[1].deck.filter(Hero.class, "").keySet().toArray()[0];

        players[0].hand.remove(hero1);
        players[1].hand.remove(hero2);

        Cell cell1 = map.cell[2][0];
        Cell cell2 = map.cell[2][0];

        hero1.cell = cell1;
        hero2.cell = cell2;

        cell1.attacker = hero1;
        cell2.attacker = hero2;
    }

    public Match(Account account1, Account account2) {
        players[0] = new Player(account1);
        players[1] = new Player(account2);
        putHeroes();
    }
}
