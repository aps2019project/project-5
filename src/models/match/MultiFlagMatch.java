package models.match;

import models.Account;
import models.Collection;
import models.Player;
import models.items.Flag;

import java.util.List;

public class MultiFlagMatch extends Match {
    List<Flag> flags;

    public MultiFlagMatch(Account account1, Account account2) {
        super(account1, account2);
    }

    public void setFlags() {
        // TODO: 4/29/19 make a random function to set flags
    }

    @Override
    public Player getWinner() {

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
