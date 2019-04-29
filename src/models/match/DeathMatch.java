package models.match;

import models.Player;

import java.text.DecimalFormat;

public class DeathMatch extends Match {
    public DeathMatch(Player player1, Player player2) {
        super(player1, player2);
    }

    @Override
    public Player getWinner() {
        for (int i = 0; i < PLAYERS_COUNT; i++) {
            if(players[i].getDeck().getHero().getCurrentHealth() <= 0)
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
