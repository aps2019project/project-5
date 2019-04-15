package views.menus;

import views.Command;

import java.util.ArrayList;

public class AccountMenu implements Menu {
    public ArrayList<Command> commands = new ArrayList<>();

    public AccountMenu() {
        // TODO: Add Commands
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    private void createAccount() {}
    private void login() {}
    private void showRanking() {}
    private void save() {}
    private void logount() {}
    private void logout() {}
}
