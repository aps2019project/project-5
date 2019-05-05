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
    CARD_NOT_FOUND("Card not found in collection."),
    ITEMS_ARE_FULL("You have 3 items and can't have any more!"),
    DECK_FULL_EXCEPTION("Deck is full!"),
    HERO_EXISTS_IN_DECK("Hero Exists in deck"),
    DECK_IS_NOT_COMPLETE("Deck is not completed!"),
    HERO_NOT_EXISTS_IN_DECK("Hero does not exist"),
    WRONG_CHOICE("Wrong Choice !"),
    PLAYERS_DECK_IS_NOT_VALID("Player's deck is not valid"),
    CANT_PLAY_WITH_YOURSELF("You can't play with yourself."),
    INVALID_MOVE("invalid target!"),
    SELECTED_DECK_IS_INVALID("Selected deck is invalid");


    private String message;

    Error(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
