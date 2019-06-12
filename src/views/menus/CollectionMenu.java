package views.menus;

import controllers.logic.Manager;
import models.Account;
import models.Collection;
import models.Deck;
import models.cards.Card;
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
        commands.add(new Command(
                "^(?i)return$",
                "",
                "return",
                "\t\t\t\t\t\t\t\treturn to MainMenu."
        ));

        commands.add(new Command(
                "^(?i)create\\s+(?i)deck\\s+(?<name>\\w+)$",
                "createDeck",
                "create deck [DeckName]",
                "\t\t\t\tcreate new empty deck."
        ));

        commands.add(new Command(
                "^(?i)delete\\s+(?i)deck\\s+(?<name>\\w+)$",
                "deleteDeck",
                "delete deck [DeckName]",
                "\t\t\t\tdeletes the deck."
        ));

        commands.add(new Command(
                "^(?i)show\\s+(?i)all\\s+(?i)decks$",
                "showAllDecks",
                "show all decks",
                "\t\t\t\t\t\tshows all decks details."
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));

        commands.add(new Command(
                "^(?i)add\\s+(?<card>[A-z ]+)\\s+to\\s+deck\\s+(?<deck>\\w+)$",
                "addCardToDeck",
                "add [CardName] to deck [DeckName]",
                "\tAdds card to deck."
        ));

        commands.add(new Command(
                "^(?i)remove\\s+(?<card>[A-z ]+)\\s+from\\s+deck\\s+(?<deck>\\w+)$",
                "removeCardFromDeck",
                "remove [CardName] from deck [DeckName]",
                "Removes card from deck."
        ));

        commands.add(new Command(
                "^(?i)search\\s+(?<cardName>[A-z ]+)$",
                "searchCard",
                "search [String]",
                "\t\t\t\t\t\tShows cards in player's collection that their name contains [String]"
        ));

        commands.add(new Command(
                "^(?i)validate\\s+deck\\s+(?<deck>\\w+)$",
                "validateDeck",
                "validate deck [DeckName]",
                "\t\t\tCheck if deck is valid or isn't"
        ));

        commands.add(new Command(
                "^(?i)select\\s+deck\\s+(?<deck>\\w+)$",
                "selectDeck",
                "select deck [DeckName]",
                "\t\t\t\tSets deck as main deck"
        ));

        commands.add(new Command(
                "^(?i)show\\s+deck\\s+(?<deck>\\w+)$",
                "showDeck",
                "show deck [DeckName]",
                "\t\t\t\tShows cards of deck."
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
        String cardName = matcher.group("cardName");
        try {
            List<Card> cards = Manager.searchMyCard(cardName);
            cards.forEach(
                    (card) -> Output.log(card.toString())
            );
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND_IN_COLLECTION);
        }

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
        String cardId = matcher.group("card");
        String deckName = matcher.group("deck");
        try {
            Manager.addCardToDeck(cardId, deckName);
            Output.log(Log.CARD_ADDED_TO_DECK);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        } catch (Deck.HeroNotExistsInDeckException e) {
            Output.err(Error.HERO_NOT_EXISTS_IN_DECK);
        } catch (Deck.DeckFullException e) {
            Output.err(Error.DECK_FULL_EXCEPTION);
        } catch (Deck.HeroExistsInDeckException e) {
            Output.err(Error.HERO_EXISTS_IN_DECK);
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND_IN_COLLECTION);
        }

    }

    public static void removeCardFromDeck(Matcher matcher) {
        String cardName = matcher.group("card");
        String deckName = matcher.group("deck");
        try {
            Manager.removeCardFromDeck(cardName, deckName);
            Output.log(Log.CARD_REMOVED_FROM_COLLECTION);
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND_IN_COLLECTION);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }

    }

    public static void validateDeck(Matcher matcher) {
        String deckName = matcher.group("deck");
        try {
            if (Manager.isValid(deckName))
                Output.log(Log.DECK_IS_COMPLETED);
            else Output.err(Error.DECK_IS_NOT_COMPLETE);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }
    }

    public static void selectDeck(Matcher matcher) {
        String deckName = matcher.group("deck");
        try {
            Manager.selectDeck(deckName);
            Output.log(Log.DECK_SELECTED);
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }
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
        String deckName = matcher.group("deck");
        try {
            Deck deck = Manager.getDeck(deckName);
            Output.log(deck.toString());
        } catch (Account.DeckNotFoundException e) {
            Output.err(Error.DECK_NOT_FOUND);
        }

    }

    public static void help(Matcher matcher) {
        Menu.help(new CollectionMenu().getCommands());
    }

}
