package views;

public enum Log {
    ACCOUNT_CREATED("Account created."),
    DECK_CREATED("Deck created."),
    DECK_DELETED("Deck deleted."),
    LOGGED_IN("You logged in."),
    BUYING_SUCCESSFUL("buying successful."),
    SELLING_SUCCESSFUL("selling successful"),
    EMPTY_COLLECTION("collection is empty.");


    private String message;

    Log(String message) {
        this.message = message;
    }
    public void printAmount(int drake){

    }
    @Override
    public String toString() {
        return this.message;
    }
}