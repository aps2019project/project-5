package server.data;

public enum Files {
    USER_DATA("user.json"),
    CARD_DATA("card.json");

    String url;

    Files(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
