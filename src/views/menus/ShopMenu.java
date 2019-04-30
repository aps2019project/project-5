package views.menus;

import controllers.Manager;
import models.Account;
import models.Collection;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.UsableItem;
import models.Collection.CardNotFoundException;
import views.Command;
import views.Error;
import views.Log;
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
                "",
                "return",
                "return to MainMenu."
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));
        commands.add(new Command(
                "^(?i)show$",
                "show",
                "show",
                "Show all cards and items."
        ));
        commands.add(new Command(
                "^(?i)buy\\s+(?<cardName>[A-z ]+)$",
                "buy",
                "buy [CardName]",
                "\t\t\tbuy card and add to account collection."
        ));
        commands.add(new Command(
                "^(?i)get(\\s+)amount$",
                "getAmount",
                "get amount",
                "\t\t\t\tshows the amount in drakes."
        ));
        commands.add(new Command(
                "^(?i)show\\s+collection$",
                "showMyCollection",
                "show collection",
                "\t\t\tshow all cards in account's collection"
        ));
        commands.add(new Command(
                "^(?i)search\\s+(?<cardName>[A-z ]+)$",
                "search",
                "search [String]",
                "\t\t\tshow all cards that their name's contains '[String]'"
        ));
        commands.add(new Command(
                "^(?i)search\\s+collection\\s+(?<cardName>[A-z ]+)$",
                "searchCollection",
                "search collection [String]",
                "show all cards in account's collection that their name contains '[String]'"
        ));

        commands.add(new Command(
                "^(?i)sell\\s+(?<cardID>\\d+)$",
                "sell",
                "sell [CardId]",
                "\t\t\tsell cards in collection"
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
            Manager.searchCardInShop(cardName).forEach(
                    card -> Output.log(card.toString()));
        } catch (CardNotFoundException e) {
            Output.err(e);
        }
    }

    public static void searchCollection(Matcher matcher) {
        String cardName = matcher.group("cardName");
        try {
            Manager.searchMyCard(cardName).forEach(
                    card -> Output.log(card.toString()));
        } catch (CardNotFoundException e) {
            Output.err(e);
        }
    }

    public static void buy(Matcher matcher) {
        String cardName = matcher.group("cardName");
        try {
            Manager.buy(cardName);
            Output.log(Log.BUYING_SUCCESSFUL);
        } catch (CardNotFoundException e) {
            Output.err(Error.CARD_NOT_EXISTS_IN_SHOP);
        } catch (Account.NotEnoughDrakeException e) {
            Output.err(Error.NOT_ENOUGH_DRAKE);
        } catch (Collection.ItemsFullException e) {
            Output.err(Error.ITEMS_ARE_FULL);
        }
    }

    public static void sell(Matcher matcher) {
        int cardID = Integer.parseInt(matcher.group("cardID"));
        try {
            Manager.sell(cardID);
            Output.log(Log.SELLING_SUCCESSFUL);
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

    public static void show(Matcher matcher) {
        try {
            showCollection(Manager.getShopCollection(), "Buy");
        } catch (Collection.NullCollectionException e) {
            Output.err(e);
        }
    }

    public static void showCollection(Collection collection, String buyOrSell) {
        List<Hero> heroes = new LinkedList<>(collection.getHeroes());
        Output.log("Heroes :");
        for (int i = 0; i < heroes.size(); i++) {
            Output.log("\t\t" + (i + 1) + " : " + heroes.get(i) + " - " + buyOrSell + " Cost : " +
                    heroes.get(i).getPrice() + "$");
        }


        List<UsableItem> Items = new LinkedList<>(collection.getUsableItems());
        Output.log("Items :");
        for (int i = 0; i < Items.size(); i++) {
            Output.log("\t\t" + (i + 1) + " : " + Items.get(i) + " - " + buyOrSell + " Cost : " +
                    Items.get(i).getPrice() + "$");
        }

        Output.log("Cards :");

        List<Minion> minions = new LinkedList<>(collection.getMinions());
        for (int i = 0; i < minions.size(); i++) {
            Output.log("\t\t" + (i + 1) + " : " + minions.get(i) + " - " + buyOrSell + " Cost : " +
                    minions.get(i).getPrice() + "$");
        }


        List<Spell> spells = new LinkedList<>(collection.getSpells());
        for (int i = 0; i < spells.size(); i++) {
            Output.log("\t\t" + (i + 1 + minions.size()) + " : " + spells.get(i) + " - " + buyOrSell + " Cost : " +
                    spells.get(i).getPrice() + "$");
        }

    }


}
