package views.menus;

import models.cards.Card;
import views.Command;
import views.Output;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenu implements Menu {

    private ArrayList<Command> commands = new ArrayList<>();

    public MainMenu() {
        commands.add(new Command("^(?i)logout$", "logout"));
        commands.add(new Command("^(?i)enter\\s+(?i)collection$", "enterCollection"));
        commands.add(new Command("^(?i)enter\\s+(?i)battle$", "enterBattle"));
        commands.add(new Command("^(?i)enter\\s+(?i)shop", "enterShop"));
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
        new BattleMenu().handleMenu();
    }

    public static void enterShop(Matcher matcher) {
        new ShopMenu().handleMenu();
    }

}
