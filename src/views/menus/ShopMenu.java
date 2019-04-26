package views.menus;

import views.Command;

import java.util.ArrayList;
import java.util.regex.Matcher;

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

    }

    @Override
    public String getMenuName() {
        return "ShopMenu";
    }

    @Override
    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void showCollection(Matcher matcher) {}
    public static void search(Matcher matcher) {}
    public static void searchCollection(Matcher matcher) {}
    public static void buy(Matcher matcher) {}
    public static void sell(Matcher matcher) {}
    public static void show(Matcher matcher) {}

    public static void help(Matcher matcher) {
        Menu.help(new ShopMenu().getCommands());
    }
}
