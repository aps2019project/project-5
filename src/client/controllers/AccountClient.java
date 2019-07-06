package client.controllers;

import models.Account;
import models.Response;

public class AccountClient {
    public static Account user;

    public static void main(String[] args) {
        login("mahdi", "mahdi");
    }

    public static Response login(String username, String password) {
        ServerConnection serverConnection = new ServerConnection("/login");
        serverConnection.parameters.put("username", username);
        serverConnection.parameters.put("password", password);
        Response response = serverConnection.getResponse();
        if(response.OK)
            user = (Account) response.data;
        return response;
    }

}