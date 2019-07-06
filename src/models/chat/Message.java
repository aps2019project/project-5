package models.chat;

public class Message {
    public String text;
    public String user;
    public long date;

    public Message(String text, String user) {
        this.text = text;
        this.user = user;
        this.date = System.currentTimeMillis();
    }
}
