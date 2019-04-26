package views;

import models.Account;
import models.Collection;
import models.Deck;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.Item;

import java.nio.channels.AcceptPendingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
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


    public static void showCardsOfShop (Collection cardsCollection) {
        List<String> cardsOrderList = new LinkedList<>(Arrays.asList(
                "Hero",
                "Item",
                "Minion",
                "Spell"));

        for (int j = 0; j < cardsOrderList.size(); j++) {
            System.out.println(cardsOrderList.get(j).toString().replace("class ", ""));
            for (int i = 1; i < cardsCollection.getCards().size(); i++) {
                Card card = (Card) cardsCollection.getCards().get(i);
                if (!card.getClass().toString().equals("class " + cardsOrderList.get(j)))
                    continue;
                if(i > 1)
                    System.out.println();
                System.out.println("\t\t" + i + " : " + card + " " + card.getPrice() + "$");
            }
        }
    }

    public static void print(Deck deck) {
        System.out.println(deck.toString());
    }
}