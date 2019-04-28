package views.menus;

import controllers.Manager;
import models.Collection;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.UsableItem;
import models.Collection.CardNotFoundException;
import views.Command;
import views.Output;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

public class ShopMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public ShopMenu() {
        commands.add(new Command(
                "^(?i)return$",
                ""
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));
        commands.add(new Command(
                "^(?i)show$",
                "show"
        ));
        commands.add(new Command(
                "^(?i)buy (?<cardName>[A-z ]+)$",
                "buy"
        ));
        commands.add(new Command(
                "^(?i)get(\\s+)amount$",
                "getAmount"
        ));
        commands.add(new Command(
                "^(?i)show collection$",
                "showMyCollection"
        ));
        commands.add(new Command(
                "^(?i)search (?<cardName>[A-z ]+)$",
                "search"
        ));
        commands.add(new Command(
                "^(?i)search collection (?<cardName>[A-z ]+)$",
                "searchCollection"
        ));
        commands.add(new Command(
                "^(?i)sell (?<cardID>//d+)$",
                "sell"
        ));
    }

    @Override
    public String getMenuName() {
        return "ShopMenu";
    }

    @Override
    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void showMyCollection(Matcher matcher) {
        try {
            showCollection(Manager.getMyCollection(), "Sell");
        } catch (Collection.NullCollectionException npException) {
            Output.err(npException);
        }
    }

    public static void search(Matcher matcher) {
        String cardName = matcher.group("cardName");
        try {
            Manager.searchCardInShop(cardName).stream().forEach(
                    card -> Output.log(card.toString()));
        } catch (CardNotFoundException e) {
            Output.err(e);
        }
    }

    public static void searchCollection(Matcher matcher) {
        String cardName = matcher.group("cardName");
        try {
            Manager.searchMyCard(cardName).stream().forEach(
                    card -> Output.log(card.toString()));
        } catch (CardNotFoundException e) {
            Output.err(e);
        }
    }

    public static void buy(Matcher matcher) {
        String cardName = matcher.group("cardName");
        try {
            Manager.buy(cardName);
        } catch (Exception e) {
            Output.err(e);
        }
    }

    public static void sell(Matcher matcher) {
        int cardID = Integer.parseInt(matcher.group("cardID"));
        try {
            Manager.sell(cardID);
        } catch (CardNotFoundException e) {
            Output.err(e);
        }

    }

    public static void help(Matcher matcher) {
        Menu.help(new ShopMenu().getCommands());
    }

    public static void getAmount(Matcher matcher) {
        Output.log(Integer.toString(Manager.getAccount().getDrake()));
    }

    public static void show (Matcher matcher) {
        try {
            showCollection(Manager.getShopCollection(), "Buy");
        } catch (Collection.NullCollectionException e) {
            Output.err(e);
        }
    }

    public static void showCollection (Collection collection, String buyOrSell) {
        List<Hero> heroes = new LinkedList<>(collection.getHeroes());
        Output.log("Heroes :");
        for (int i = 0; i < heroes.size(); i++) {
            Output.log("\t\t" + i + " : " + heroes.get(i) + " - " + buyOrSell + " Cost : " +
                    heroes.get(i).getPrice() + "$");
        }


        List<UsableItem> Items = new LinkedList<>(collection.getUsableItems());
        Output.log("Items :");
        for (int i = 0; i < Items.size(); i++) {
            Output.log("\t\t" + i + " : " + Items.get(i) + " - " + buyOrSell + " Cost : " +
                    Items.get(i).getPrice() + "$");
        }

        Output.log("Cards :");

        List<Minion> minions = new LinkedList<>(collection.getMinions());
        for (int i = 0; i < minions.size(); i++) {
            Output.log("\t\t" + (i + 1) + " : " + minions.get(i) + " - " + buyOrSell + " Cost : " +
                    minions.get(i).getPrice() + "$") ;
        }


        List<Spell> spells = new LinkedList<>(collection.getSpells());
        for (int i = 0; i < spells.size(); i++) {
            Output.log("\t\t" + (i + 1 + minions.size()) + " : " + spells.get(i) + " - " + buyOrSell + " Cost : " +
                    spells.get(i).getPrice() + "$");
        }

    }



}
