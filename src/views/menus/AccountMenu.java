package views.menus;

import views.Command;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AccountMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public AccountMenu() {
        // TODO: Add Commands
        commands.add(new Command("^(?i)exit$", ""));
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void createAccount(Matcher matcher) {}
    public static void login(Matcher matcher) {}
    public static void showRanking(Matcher matcher) {}
    public static void save(Matcher matcher) {}
    public static void logount(Matcher matcher) {}
    public static void logout(Matcher matcher) {}
}
