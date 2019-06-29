package models;

public class Account {
    public String username, password;
    public String token;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
