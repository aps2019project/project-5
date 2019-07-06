package client.controllers;

import models.Response;

public class CollectionClient {
    public Response search(String token, String search, String type) {
        ServerConnection serverConnection = new ServerConnection("/collection/search");
        serverConnection.parameters.put("token", token);
        if (!search.equals("")) serverConnection.parameters.put("search", search);
        if (!type.equals("")) serverConnection.parameters.put("type", type);
        return serverConnection.getResponse();
    }
}
