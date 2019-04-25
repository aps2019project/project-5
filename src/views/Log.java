package views;

public enum Log {
    ACCOUNT_CREATED("Account created."),
    DECK_CREATED("Deck created."),
    LOGGED_IN("You logged in.");

    private String message;

    Log(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}