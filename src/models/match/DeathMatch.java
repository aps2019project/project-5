package models.match;

import controllers.Manager;
import models.Account;
import models.Player;

import java.util.LinkedList;

public class DeathMatch extends Match {
    public DeathMatch(Account account1 , Account account2) {
        super(account1, account2);
    }

    @Override
    public Player getWinner() {
        for (int i = 0; i < PLAYERS_COUNT; i++) {
            if(Manager.getHero(new LinkedList<>(players[i].getActiveCards())).get(0).getCurrentHealth()  <= 0)
                return players[(i + 1) % 2];
        }
        return null;
    }

    @Override
    public String getInfo() {
        String result = "";
        for (int i = 0; i < PLAYERS_COUNT; i++) {
            result += "Player number " + (i + 1) + " Hero HP : " +
                    players[i].getDeck().getHero().getCurrentHealth() ;
            if(i == 0)
                result += "\n";
        }
        return result;
    }
}
