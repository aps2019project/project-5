package views.menus;

import controllers.Manager;
import models.Account;
import views.Command;
import views.Error;
import views.Log;
import views.Output;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CollectionMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public CollectionMenu() {
        // TODO: Add Commands
        commands.add(new Command("^(?i)return", ""));
        commands.add(new Command("^(?i)create\\s+(?i)deck\\s+(?<name>\\w+)$", "createDeck"));
    }

    @Override
    public String getMenuName() {
        return "CollectionMenu";
    }

    @Override
    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void searchCard(Matcher matcher) {
        // TODO: Implement...

    }

    public static void createDeck(Matcher matcher) {
        String deckName = matcher.group("name");
        try {
            Manager.createDeck(deckName);
            Output.log(Log.DECK_CREATED);
        } catch (Account.NotLoggedInException e) {
            Output.err(Error.NOT_LOGGED_IN);
        } catch (Account.DeckExistsException e) {
            Output.err(Error.DECK_EXISTS);
        }
    }

    public static void deleteDeck(Matcher matcher) {
        // TODO: Implement...

    }

    public static void addCardToDeck(Matcher matcher) {
        // TODO: Implement...

    }

    public static void removeCardFromDeck(Matcher matcher) {
        // TODO: Implement...

    }

    public static void validateDeck(Matcher matcher) {
        // TODO: Implement...

    }

    public static void selectDeck(Matcher matcher) {
        // TODO: Implement...

    }

    public static void showAllDecks(Matcher matcher) {
        // TODO: Implement...

    }

    public static void showDeck(Matcher matcher) {
        // TODO: Implement...

    }

}
