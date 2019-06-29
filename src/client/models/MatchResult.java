package client.models;

import java.util.Date;

public class MatchResult {
    private Account opponent;
    private boolean won;
    private Date matchDate;

    public MatchResult(Account opponent, boolean won, Date matchDate) {
        this.opponent = opponent;
        this.won = won;
        this.matchDate = matchDate;
    }

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
