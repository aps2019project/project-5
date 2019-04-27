package views.menus;

import controllers.Manager;
import models.Account;
import models.Collection;
import models.Shop;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.Item;
import models.items.UsableItem;
import models.match.Match;
import models.Collection.CardNotFoundException;
import views.Command;
import views.Error;
import views.Log;
import views.Output;

import javax.naming.ldap.ManageReferralControl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import models.Collection.ItemsFullException;

public class ShopMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public ShopMenu() {
        // TODO: Add Commands
        commands.add(new Command(
                "^(?i)return$",
                ""
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));
        commands.add(new Command(
                "^(?i)show",
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
                "^(?i)show collection",
                "showMyCollection"
        ));
        commands.add(new Command(
                "^(?i)search (?<cardName>[A-z ]+)",
                "search"
        ));
        commands.add(new Command(
                "^(?i)sell (?<cardID>//d+)",
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
        showCollection(Manager.getMyCollection(), "Sell");
    }

    public static void search(Matcher matcher) {
        String cardName = matcher.group("cardName");
        try {
            Output.log(Manager.searchCard(cardName));
        } catch (CardNotFoundException e) {
            Output.err(e);
        }
    }

    public static void searchCollection(Matcher matcher) {

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
        showCollection(Manager.getShopCollection(), "Buy");
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

        List<Minion> minions = new LinkedList<>(collection.getMinions());
        Output.log("Minions :");
        for (int i = 0; i < minions.size(); i++) {
            Output.log("\t\t" + i + " : " + minions.get(i) + " - " + buyOrSell + " Cost : " +
                    minions.get(i).getPrice() + "$") ;
        }


        List<Spell> spells = new LinkedList<>(collection.getSpells());
        Output.log("Spells :");
        for (int i = 0; i < spells.size(); i++) {
            Output.log("\t\t" + i + " : " + spells.get(i) + " - " + buyOrSell + " Cost : " +
                    spells.get(i).getPrice() + "$");
        }

    }



}
