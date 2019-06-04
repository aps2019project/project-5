package views.menus;

import controllers.logic.Manager;
import models.Account;
import views.*;
import views.Error;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AccountMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public AccountMenu() {
        // TODO: Add Commands
        commands.add(new Command(
                "^(?i)exit$",
                "",
                "exit",
                "\t\t\t\t\t\texit game"
        ));

        commands.add(new Command(
                "^(?i)create\\s+(?i)account\\s+(?<username>\\w+)$",
                "createAccount",
                "create account [username]",
                "\tgets password and creates the account."
        ));

        commands.add(new Command(
                "^(?i)login\\s+(?<username>\\w+)$",
                "login",
                "login [username]",
                "\t\t\tgets password logs in the user."
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));

        commands.add(new Command(
                "^(?i)show\\s+(?i)leaderboard$",
                "showRanking",
                "show leaderboard",
                "\t\t\tprints players ordered by their win count."
        ));
    }

    @Override
    public String getMenuName() {
        return "AccountMenu";
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
            Output.log(Log.ACCOUNT_CREATED);
        } catch (Account.UsernameExistsException exception) {
            Output.err(Error.USERNAME_EXISTS);
        }
    }

    public static void login(Matcher matcher) {
        String username = matcher.group("username");
        String password = Input.getString("Password: ");
        try {
            Manager.login(username, password);
            Output.log(Log.LOGGED_IN);
            new MainMenu().handleMenu();
        } catch (Account.InvalidPasswordException e) {
            Output.err(Error.WRONG_PASSWORD);
        } catch (Account.InvalidUsernameException e) {
            Output.err(Error.USERNAME_NOT_FOUND);
        }
    }

    public static void showRanking(Matcher matcher) {
        ArrayList<Account> accounts = Manager.getLeaderboard();
        for(Account account : accounts) {
            Output.print(account);
        }
    }

    public static void save(Matcher matcher) {
        // TODO: Call controller functions
    }

    public static void help(Matcher matcher) {
        Menu.help(new AccountMenu().getCommands());
    }
}
