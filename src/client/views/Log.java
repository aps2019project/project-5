package client.views;

public enum Log {
    ACCOUNT_CREATED("Account created."),
    DECK_CREATED("Deck created."),
    DECK_DELETED("Deck deleted."),
    LOGGED_IN("You logged in."),
    BUYING_SUCCESSFUL("buying successful."),
    SELLING_SUCCESSFUL("selling successful"),
    EMPTY_COLLECTION("collection is empty."),
    CARD_ADDED_TO_DECK("Card added to deck."),
    DECK_IS_COMPLETED("Deck is complete!"),
    CARD_REMOVED_FROM_COLLECTION("Card removed from deck"),
    DECK_IS_INVALID("Deck is invalid"),
    DECK_SELECTED("Deck selected.");


    private String message;

    Log(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}