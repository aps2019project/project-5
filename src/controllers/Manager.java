package controllers;

import models.Account;
import models.match.Match;

public class Manager {
    private static Account account;
    private static Match playingMatch;

    public static Account getAccount() {
        return account;
    }

    public static void addAccount() {
        Account account1 = new Account(account.getUsername(), account.getPassword());
        try {
            Account.AddUser(account1);
        }catch (Account.userNameExistsException e){
            System.out.println("player existed!");
        }
    }

    public static Match getPlayingMatch() {
        return playingMatch;
    }
}
