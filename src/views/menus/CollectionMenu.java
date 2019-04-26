package views.menus;

import controllers.Manager;
import models.Account;
import models.Deck;
import views.Command;
import views.Error;
import views.Log;
import views.Output;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class CollectionMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public CollectionMenu() {
        // TODO: Add Commands
        commands.add(new Command(
                "^(?i)return$",
                ""
        ));

        commands.add(new Command(
                "^(?i)create\\s+(?i)deck\\s+(?<name>\\w+)$",
                "createDeck"
        ));

        commands.add(new Command(
                "^(?i)delete\\s+(?i)deck\\s+(?<name>\\w+)$",
                "createDeck"
        ));

        commands.add(new Command(
                "^(?i)show\\s+(?i)all\\s+(?i)decks$",
                "showAllDecks"
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));

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
        String deckName = matcher.group("name");
        try {
            Manager.deleteDeck(deckName);
            Output.log(Log.DECK_DELETED);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }

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
        try {
            List<Deck> decks = Manager.getDecks();
            for(Deck deck : decks)
                Output.print(deck);
        } catch (Account.NotLoggedInException e) {
            Output.err(Error.NOT_LOGGED_IN);
        }
    }

    public static void showDeck(Matcher matcher) {
        // TODO: Implement...

    }

    public static void help(Matcher matcher) {
        Menu.help(new CollectionMenu().getCommands());
    }

}
