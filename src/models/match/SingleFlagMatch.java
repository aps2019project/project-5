package models.match;

import models.Player;
import models.items.Flag;

public class SingleFlagMatch extends Match {


    private int WINNING_TURN = 12;
    Flag flag ;

    public SingleFlagMatch(){
        super();
    }

    @Override
    public Player getWinner() {
        for (int i = 0; i < PLAYERS_COUNT; i++) {
            if(!players[i].hasFlag())
                continue;
            if(getTurn() - players[i].getFlags().get(0).getTokenTurn() >= WINNING_TURN ) {
                return players[i];
            }
        }
        return null;
    }

    @Override
    public String getInfo() {
        String result = "Flag is in " + flag.getCell() + " cell and ";
        if(flag.isGotten())
            result += "in hand of player number " + (flag.getTokenTurn() % 2 + 1) +
                    " attacker "  + flag.getOwner().getName() +
                    ".";
        else
            result += "is not token.";
        return result;
    }
}
