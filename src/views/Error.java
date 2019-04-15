package views;

public enum Error {
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
