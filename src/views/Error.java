package views;

public enum Error {
    USERNAME_NOT_FOUND("Username not found"),
    USERNAME_EXISTS("Username exists"),
    WRONG_PASSWORD("Wrong password"),
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
