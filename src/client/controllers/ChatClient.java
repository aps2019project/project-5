package client.controllers;

import models.Response;

public class ChatClient {
    public static String token = AccountClient.user.loginToken;

    public static Response sendMessage(String message) {
        ServerConnection serverConnection = new ServerConnection("/chat/send_message");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("text", message);
        return serverConnection.getResponse();
    }

    public static Response update() {
        ServerConnection serverConnection = new ServerConnection("/chat/update");
        serverConnection.parameters.put("token", token);
        return serverConnection.getResponse();
    }

}
