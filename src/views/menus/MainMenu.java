package views.menus;

import controllers.Manager;
import models.cards.Card;
import views.Command;
import views.Output;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenu implements Menu {

    private ArrayList<Command> commands = new ArrayList<>();

    public MainMenu() {
        // TODO: Add commands...

        commands.add(new Command(
                "^(?i)logout$",
                "logout",
                "logout",
                "\t\t\tlogout and return to account menu."
        ));

        commands.add(new Command(
                "^(?i)enter\\s+(?i)collection$",
                "enterCollection",
                "enter collection",
                "enter collection menu, create and edit decks."
        ));

        commands.add(new Command(
                "^(?i)enter\\s+(?i)battle$",
                "enterBattle",
                "enter battle",
                "\tenter battle menu and battle with other player or computer."
        ));

        commands.add(new Command(
                "^(?i)enter\\s+(?i)shop",
                "enterShop",
                "enter shop",
                "\t\tenter shop menu and buy new cards."
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));

    }

    @Override
    public String getMenuName() {
        return "MainMenu";
    }

    @Override
    public ArrayList<Command> getCommands() {
        return commands;
    }

    public static void enterCollection(Matcher matcher) {
        new CollectionMenu().handleMenu();
    }

    public static boolean logout(Matcher matcher) {
        return false;
    }

    public static void enterBattle(Matcher matcher) {
//        if(Manager.getAccount().getMainDeck().isValid())
            new BattleMenu().handleMenu();
//        else
//            throw new DeckNotValidException();
    }

    public static void enterShop(Matcher matcher) {
        new ShopMenu().handleMenu();
    }

    public static void help(Matcher matcher) {
        Menu.help(new MainMenu().getCommands());
    }
}
