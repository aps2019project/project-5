package controllers;

import models.Match;
import models.auth.Account;

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
