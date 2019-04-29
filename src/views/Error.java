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
    CARD_NOT_FOUND("Card not found in collection."),
    ITEMS_ARE_FULL("You have 3 items and can't have any more!"),
    CARD_EXISTS_IN_DECK("Card exists in the deck"),
    DECK_FULL_EXCEPTION("Deck is full!"),
    HERO_EXISTS_IN_DECK("Hero Exists in deck"),
    DECK_IS_NOT_COMPLETE("Deck is not completed!");
    private String message;

    Error(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
