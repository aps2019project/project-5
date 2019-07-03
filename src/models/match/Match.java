package models.match;

import models.Account;
import models.map.Map;

public class Match {
    public Map map = new Map();
    public Player[] players = new Player[2];

    public Match(Account account1, Account account2) {
        players[0] = new Player(account1);
        players[1] = new Player(account2);

    }
}
