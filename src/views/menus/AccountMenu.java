package views.menus;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.logic.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Account;
import views.*;
import views.Error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class AccountMenu implements Menu {
    public JFXTextField loginUsername, signupUsername;
    public JFXPasswordField loginPassword, signupPassword;
    public JFXButton loginButton, signupButton;
    private ArrayList<Command> commands = new ArrayList<>();

    public ListView listView;

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

    public void showRanking(Matcher matcher) {
        try {
            Stage rankWin = new Stage();
            Parent root = FXMLLoader.load(Graphics.class.getResource("layouts/ranking.fxml"));
            Scene scene = new Scene(root, 500, 500);
            rankWin.initModality(Modality.WINDOW_MODAL);
            rankWin.setScene(scene);
            ArrayList<Account> accounts = Manager.getLeaderboard();
            listView.getItems().addAll(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(Matcher matcher) {
        // TODO: Call controller functions
    }

    public static void help(Matcher matcher) {
        Menu.help(new AccountMenu().getCommands());
    }
}
