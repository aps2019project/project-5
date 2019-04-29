package views.menus;

import controllers.Manager;
import models.Account;
import models.Collection;
import models.Deck;
import models.cards.Card;
import views.Command;
import views.Error;
import views.Log;
import views.Output;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;

public class CollectionMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public CollectionMenu() {
        // TODO: Add Commands
        commands.add(new Command(
                "^(?i)return$",
                "",
                "return",
                "\t\t\t\treturn to MainMenu."
        ));

        commands.add(new Command(
                "^(?i)create\\s+(?i)deck\\s+(?<name>\\w+)$",
                "createDeck",
                "create deck [DeckName]",
                "create new empty deck."
        ));

        commands.add(new Command(
                "^(?i)delete\\s+(?i)deck\\s+(?<name>\\w+)$",
                "deleteDeck",
                "delete deck [DeckName]",
                "deletes the deck."
        ));

        commands.add(new Command(
                "^(?i)show\\s+(?i)all\\s+(?i)decks$",
                "showAllDecks",
                "show all decks",
                "\t\tshows name of all decks."
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));

        commands.add(new Command(
                "^(?i)add\\s+(?<card>[A-z ]+)\\s+to\\s+deck\\s+(?<deck>\\w+)$",
                "addCardToDeck"
        ));
        commands.add(new Command(
                "^(?i)remove\\s+(?<card>[A-z ]+)\\s+from\\s+deck\\s+(?<deck>\\w+)$",
                "removeCardFromDeck"
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
        String cardId = matcher.group("card");
        String deckName = matcher.group("deck");
        try {
            Manager.addCardToDeck(cardId, deckName);
            Output.log(Log.CARD_ADDED_TO_DECK);
        } catch (Deck.CardExistsInDeckException e) {
            Output.err(Error.CARD_EXISTS_IN_DECK);
        } catch (Deck.HeroExistsInDeckException e) {
            Output.err(Error.HERO_EXISTS_IN_DECK);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        } catch (Deck.DeckFullException e) {
            Output.err(Error.DECK_FULL_EXCEPTION);
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND);
        }

    }

    public static void removeCardFromDeck(Matcher matcher) {
        // TODO: Implement...
        String cardName = matcher.group("card");
        String deckName = matcher.group("deck");
        try {
            Manager.removeCardFromDeck(cardName, deckName);
            Output.log(Log.CARD_REMOVED_FROM_COLLECTION);
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }

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
            for (Deck deck : decks)
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
