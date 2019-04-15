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

    private void exit() {}
    private void searchCard(String name) {}
    private void searchItem(String name) {}
    private void save() {}
    private void createDeck(String deckName) {}
    private void deleteDeck(String deckName) {}
    private void addCardToDeck(int deckId, int cardId) {}
    private void removeCardFromDeck(int deckId, int cardId) {}
    private void validateDeck(String deckName) {}
    private void selectDeck(String deckName) {}
    private void showAllDecks() {}
    private void showDeck(String deckName) {}
}
