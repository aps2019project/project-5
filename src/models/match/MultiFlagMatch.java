package models.match;

import models.Player;
import models.items.Flag;

import java.util.List;

public class MultiFlagMatch extends Match {
    List<Flag> flags;

    public MultiFlagMatch(Player player1, Player player2) {
        super(player1, player2);
    }

    public void setFlags() {
        // TODO: 4/29/19 make a random function to set flags
    }

    @Override
    public Player getWinner() {
        for (int i = 0; i < PLAYERS_COUNT; i++) {
            if (players[i].getFlags().size() >= 1. * flags.size() / 2)
                return players[i];
        }
        return null;
    }

    @Override
    public String getInfo() {
        String result = "";
        for (int i = 0; i < flags.size(); i++) {
            Flag flag = flags.get(i);
            if (!flag.isGotten())
                continue;
            result += flag.getName() + " is token by attacker " +
                    flag.getOwner().getName() + " of player number " +
                    flag.getTokenTurn() % 2;
            if (i != flags.size() - 1)
                result += "\n";
        }
        return result;
    }


}
