package client.controllers;

import models.Response;

import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        login("mahdi", "mahdi");
    }

    public static void login(String username, String password) {
        ServerConnection serverConnection = new ServerConnection("/login");
        serverConnection.parameters.put("username", username);
        serverConnection.parameters.put("password", password);
        Response response = serverConnection.getResponse();
        System.out.println(response.message);

    }

}