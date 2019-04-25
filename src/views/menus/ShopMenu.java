package views.menus;

import views.Command;

import java.util.ArrayList;

public class ShopMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public ShopMenu() {
        // TODO: Add Commands
    }

    @Override
    public String getMenuName() {
        return "ShopMenu";
    }

    @Override
    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void showCollection() {}
    public static void search(String name) {}
    public static void searchCollection(String name) {}
    public static void buy(String name) {}
    public static void sell(int id) {}
    public static void show() {}
}
