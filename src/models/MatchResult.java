package models;

import java.util.Date;

public class MatchResult {
    private Account opponent;
    private boolean won;
    private Date matchDate;


    public Account getOpponent() {
        return opponent;
    }

    public boolean won() {
        return this.won;
    }

    public Date getMatchDate() {
        return this.matchDate;
    }
}
