package models.match;

import models.Account;
import models.Collection;
import models.Player;
import models.items.Flag;

public class SingleFlagMatch extends Match {


    private int WINNING_TURN = 12;
    Flag flag = new Flag();

    public SingleFlagMatch(Account account1 , Account account2) {
        super(account1, account2);
    }

    @Override
    public Player getWinner() {

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
