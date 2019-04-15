package views.menus;

import views.Command;

import java.util.ArrayList;

public class CollectionMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public CollectionMenu() {
        // TODO: Add Commands
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void exit() {}
    public static void searchCard(String name) {}
    public static void searchItem(String name) {}
    public static void save() {}
    public static void createDeck(String deckName) {}
    public static void deleteDeck(String deckName) {}
    public static void addCardToDeck(int deckId, int cardId) {}
    public static void removeCardFromDeck(int deckId, int cardId) {}
    public static void validateDeck(String deckName) {}
    public static void selectDeck(String deckName) {}
    public static void showAllDecks() {}
    public static void showDeck(String deckName) {}
}
