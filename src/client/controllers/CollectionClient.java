package client.controllers;

import models.Response;

public class CollectionClient {
    private String token = AccountClient.user.loginToken;

    public Response search(String search, String type) {
        ServerConnection serverConnection = new ServerConnection("/collection/search");
        serverConnection.parameters.put("token", token);
        if (!search.equals("")) serverConnection.parameters.put("search", search);
        if (!type.equals("")) serverConnection.parameters.put("type", type);
        return serverConnection.getResponse();
    }

    public Response createDeck(String name, String search, String type) {
        ServerConnection serverConnection = new ServerConnection("/collection/create_deck");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("name", name);

        if (!search.equals("")) serverConnection.parameters.put("search", search);
        if (!type.equals("")) serverConnection.parameters.put("type", type);
        return serverConnection.getResponse();

    }

    public Response addCardToDeck(String deckName, String cardName) {
        ServerConnection serverConnection = new ServerConnection("/collection/add_card_to_deck");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("deck_name", deckName);
        serverConnection.parameters.put("card_name", cardName);
        return serverConnection.getResponse();
    }

    public Response getDeck(String deckName) {
        ServerConnection serverConnection = new ServerConnection("/collection/get_deck");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("deck_name", deckName);
        return serverConnection.getResponse();
    }

    public Response removeDeck(String deckName) {
        ServerConnection serverConnection = new ServerConnection("/collection/remove_deck");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("deck_name", deckName);
        return serverConnection.getResponse();
    }

    public Response removeCardFromDeck(String deckName, String cardName) {
        ServerConnection serverConnection = new ServerConnection("/collection/remove_card_from_deck");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("deck_name", deckName);
        serverConnection.parameters.put("card_name", cardName);
        return serverConnection.getResponse();
    }

    public Response isValid(String deckName) {
        ServerConnection serverConnection = new ServerConnection("/collection/is_valid");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("deck_name", deckName);
        return serverConnection.getResponse();
    }

    public Response setMainDeck(String deckName) {
        ServerConnection serverConnection = new ServerConnection("/collection/set_main_deck");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("deck_name", deckName);
        return serverConnection.getResponse();
    }


}
