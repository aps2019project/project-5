package views;

public enum Error {
    USERNAME_NOT_FOUND("Username not found"),
    USERNAME_EXISTS("Username exists"),
    WRONG_PASSWORD("Wrong password"),
    DECK_EXISTS("Deck with this name exists."),
    DECK_NOT_FOUND("Deck with this name not found."),
    NOT_LOGGED_IN("You not logged in."),
    INVALID_COMMAND("Invalid command"),
    CARD_NOT_EXISTS_IN_SHOP("Card not exists in shop."),
    NOT_ENOUGH_DRAKE("Not enough drake."),
    CARD_NOT_FOUND_IN_COLLECTION("Card not found in collection."),
    CARD_NOT_FOUND("Card with this id not found"),
    ITEMS_ARE_FULL("You have 3 items and can't have any more!"),
    DECK_FULL_EXCEPTION("Deck is full!"),
    HERO_EXISTS_IN_DECK("Hero Exists in deck"),
    DECK_IS_NOT_COMPLETE("Deck is not completed!"),
    HERO_NOT_EXISTS_IN_DECK("Hero does not exist"),
    WRONG_CHOICE("Wrong Choice !"),
    PLAYERS_DECK_IS_NOT_VALID("Player's deck is not valid"),
    CANT_PLAY_WITH_YOURSELF("You can't play with yourself."),
    INVALID_MOVE("invalid move!"),
    SELECTED_DECK_IS_INVALID("Selected deck is invalid"),
    INVALID_TARGET("Invalid target"),
    NOT_ENOUGH_MANA("You don't have enough mana."),
    INVALID_CELL("Invalid cell."),
    CARD_ATTACK_IS_NOT_AVAILABLE("card with '%d' is not available"),
    OPPONENT_MINION_IS_NOT_AVAILABLE("minion opponent is not available for attack"),
    CARD_NOT_IN_HAND("Card not found in hand"),
    NOT_IN_GRAVEYARD("You are not in graveyard"),
    ALREADY_IN_GRAVEYARD( "You were already in graveyard"),
    CARD_NOT_FOUND_IN_GRAVEYARD("Card not found in graveyard"),
    NO_ITEM_SELECTED("No collectable item selected"),
    CARD_SELECTED("card selected!"),
    ITEM_SELECTED("Collectible item selected."),
    NO_ITEM("Item with this ID not found."),
    NO_ITEM_SELECTED("No collectible item selected"),
    HERO_DEATH("Your hero dead."),
    ITEM_SELECTED("Collectable item selected."),
    NO_ITEM("Item with this ID not found."),
    CELL_FULL("Cell is full");

    private String message;

    Error(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
