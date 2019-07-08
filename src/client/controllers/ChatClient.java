package client.controllers;

import models.Response;
import models.chat.Chat;

public class ChatClient {
    public static String token = AccountClient.user.loginToken;

    public static Response sendMessage(String message) {
        ServerConnection serverConnection = new ServerConnection("/chat/send_message");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("message", message);
        return serverConnection.getResponse();
    }

    public static Chat update() {
        ServerConnection serverConnection = new ServerConnection("/chat/update");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        return ((Chat) serverConnection.getResponse().data);
    }

}
