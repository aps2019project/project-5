package client.controllers;

import models.Account;
import models.Response;

public class AccountClient {
    public static Account user;

    public static Response login(String username, String password) {
        ServerConnection serverConnection = new ServerConnection("/login");
        serverConnection.parameters.put("username", username);
        serverConnection.parameters.put("password", password);
        Response response = serverConnection.getResponse();
        if (response.OK)
            user = (Account) response.data;
        return response;
    }

    public static Response signup(String username, String password) {
        ServerConnection serverConnection = new ServerConnection("/sign_up");
        serverConnection.parameters.put("username", username);
        serverConnection.parameters.put("password", password);
        return serverConnection.getResponse();
    }

    public static Response incrementDrake(int amount) {
        ServerConnection serverConnection = new ServerConnection("/cheat-mode/increment_drake");
        serverConnection.parameters.put("amount", String.valueOf(amount));
        return serverConnection.getResponse();
    }
}