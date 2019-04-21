package views.menus;

import controllers.Manager;
import models.Account;
import views.Command;
import views.Input;
import views.Output;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AccountMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public AccountMenu() {
        // TODO: Add Commands
        commands.add(new Command("^(?i)exit$", ""));
        commands.add(new Command("^(?i)create\\s+(?i)account\\s+(?<username>\\w+)$", "createAccount"));
    }

    @Override
    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void createAccount(Matcher matcher) {
        String username = matcher.group("username");
        String password = Input.getString("Password: ");
        Account account = new Account(username, password);
        try {
            Manager.addAccount(account);
        } catch (Account.UsernameExistsException exception) {
            Output.err("This username exists.");
        }
    }

    public static void login(Matcher matcher) {
        // TODO: Call controller functions
    }

    public static void showRanking(Matcher matcher) {
        // TODO: Call controller functions
    }

    public static void save(Matcher matcher) {
        // TODO: Call controller functions
    }

    public static void logount(Matcher matcher) {
        // TODO: Call controller functions
    }

    public static void logout(Matcher matcher) {
        // TODO: Call controller functions
    }
}
