package client.views;

import client.models.Account;
import client.models.Deck;
import client.models.cards.Card;
import client.models.cards.Hero;
import client.models.cards.Minion;
import client.models.cards.spell.Spell;
import client.models.items.CollectableItem;
import client.models.items.Item;

import java.util.List;

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

    public static void err (Exception e) {
        System.out.println(e.getMessage());
    }

    public static void log(Log log) {
        System.out.println(log.toString());
    }



    public static void print(Deck deck) {
        System.out.println(deck.toString());
    }

    public static void log(int number) {
        System.out.println(number);
    }

    public static void log(List<Item> collectableItems) {
        Output.log("Collected Items :");
        collectableItems.forEach(item -> {
            Output.log( "\n\t" + item.toString());
        });
    }

    public static void log(CollectableItem selectedCollectableItem) {
        Output.log(selectedCollectableItem.toString());
    }

    public static void log(Card card) {
        Output.log( "\t" + card.toString());
    }

}