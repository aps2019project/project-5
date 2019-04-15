package views.menus;

import views.Command;

import java.util.ArrayList;

public class ShopMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public ShopMenu() {
        // TODO: Add Commands
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    private void showCollection() {}
    private void search(String name) {}
    private void searchCollection(String name) {}
    private void buy(String name) {}
    private void sell(int id) {}
    private void show() {}
}
