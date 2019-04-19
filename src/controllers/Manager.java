package controllers;

import models.Account;
import models.match.Match;

public class Manager {
    private static Account account;
    private static Match playingMatch;

    public static Account getAccount() {
        return account;
    }

    public static Match getPlayingMatch() {
        return playingMatch;
    }
}
