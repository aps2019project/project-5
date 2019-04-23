package controllers;

import models.Account;
import models.match.Match;

public class Manager {
    private static Account account;
    private static Match playingMatch;

    public static Account getAccount() {
        return account;
    }

    public static void addAccount(Account account) throws Account.UsernameExistsException {
        Account.addAccount(account);
    }

    public static void login(String username, String password) throws Account.InvalidPasswordException, Account.InvalidUsernameException {
        account = Account.getAccount(username, password);;
    }

    public static void getLeaderboard() {

    }

    public static Match getPlayingMatch() {
        return playingMatch;
    }
}
