package views.menus;

import models.cards.Card;
import views.Command;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenu implements Menu {

    private ArrayList<Command> commands = new ArrayList<>();

    public MainMenu() {
        commands.add(new Command("^(?i)exit$", ""));
        commands.add(new Command("^(?i)enter\\s+(?i)collection$", "enterCollection"));
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public static void enterCollection(Matcher matcher) {

    }

}
