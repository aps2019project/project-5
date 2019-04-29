package models.match;

import controllers.Manager;
import models.Player;
import models.items.Flag;

import javax.print.MultiDoc;
import java.util.List;

public class MultiFlagMatch extends Match {
    public MultiFlagMatch(Player player1, Player player2) {
        super(player1, player2);
    }

    List<Flag> flags;

    public void setFlags() {
        // TODO: 4/29/19 make a random function to set flags
    }

    @Override
    public Player getWinner() {
        for (int i = 0; i < PLAYERS_COUNT; i++) {
            if(players[i].getFlags().size() >= 1.*flags.size()/2)
                return players[i];
        }
        return null;
    }


}
