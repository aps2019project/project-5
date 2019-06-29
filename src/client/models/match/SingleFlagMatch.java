package client.models.match;

import client.models.Account;
import client.models.Player;
import client.models.items.Flag;

public class SingleFlagMatch extends Match {


    private int WINNING_TURN = 12;
    Flag flag = new Flag();

    public SingleFlagMatch(Account account1 , Account account2) {
        super(account1, account2);
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
        if(flag.isToken())
            result += "in hand of player number " + (flag.getTokenTurn() % 2 + 1) +
                    " attacker "  + flag.getOwner().getName() +
                    ".";
        else
            result += "is not token.";
        return result;
    }
}
