package views;

public enum Error {
    INVALID_COMMAND("Invalid command"),
    ILLEGAL_ACCESS("Illegal access to method"),
    INVOCATION_TARGET_ERROR("Invocation target exception"),
    INVALID_METHOD_NAME("No such method!");

    private String message;

    Error(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
