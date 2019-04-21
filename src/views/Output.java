package views;

import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.Item;

import java.util.Iterator;

public class Print {
    // TODO: 4/16/19 Override toString()s

    public static void printHero(Hero hero){
        System.out.println(hero);
    }

    public static void printItem(Item item) {
        System.out.println(item);
    }

    public static void printMinion(Minion minion) {
        System.out.println(minion);
    }

    public static void printSpell(Spell spell) { }



public class Output {
    public static void log(String str) {
        System.out.println(str);
    }

    public static void err(String str) {
        System.err.println(str);
    }
}
