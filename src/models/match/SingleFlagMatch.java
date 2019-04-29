package models.match;

import models.Player;
import models.items.Flag;

public class SingleFlagMatch extends Match {


    private int WINNING_TURN = 12;
    Flag flag = new Flag();

    public SingleFlagMatch(Player player1, Player player2){
        super(player1, player2);
    }

    @Override
    public Player getWinner() {
        for (int i = 0; i < PLAYERS_COUNT; i++) {
            if(!players[i].hasFlag())
                continue;
            if(getTurn() - players[i].getFlags().get(0).getGotTurn() >= WINNING_TURN ) {
                return players[i];
            }
        }
        return null;
    }
}
