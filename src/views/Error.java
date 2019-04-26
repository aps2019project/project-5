package views;

public enum Error {
    USERNAME_NOT_FOUND("Username not found"),
    USERNAME_EXISTS("Username exists"),
    WRONG_PASSWORD("Wrong password"),
    DECK_EXISTS("Deck with this name exists."),
    DECK_NOT_FOUND("Deck with this name not found."),
    NOT_LOGGED_IN("You not logged in."),
    INVALID_COMMAND("Invalid command");

    private String message;

    Error(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
