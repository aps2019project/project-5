package views;

import models.Account;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.Item;

import java.nio.channels.AcceptPendingException;
import java.util.Iterator;

public class Output {
    public static void print(Hero hero) {
        System.out.println(hero);
    }

    public static void print(Item item) {
        System.out.println(item);
    }

    public static void print(Minion minion) {
        System.out.println(minion);
    }

    public static void print(Account account) {
        System.out.println(account);
    }

    public static void print(Spell spell) {
        System.out.println(spell);
    }

    public static void log(String str) {
        System.out.println(str);
    }

    public static void err(String str) {
        System.err.println(str);
    }

    public static void err(Error error) {
        System.err.println(error.toString());
    }

    public static void log(Log log) {
        System.out.println(log.toString());
    }

}