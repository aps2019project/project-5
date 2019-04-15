package models;

import models.auth.Account;

import java.util.Date;

public class MatchResult {
    private Account opponent;
    private boolean won;
    private Date matchDate;


    public String getOpponentUsername() { return opponent.getUsername(); }
    public boolean won() { return this.won; }
    public Date getMatchDate() { return this.matchDate; }
}
