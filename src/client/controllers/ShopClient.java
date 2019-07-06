package client.controllers;

import models.Account;
import models.Response;
import models.cards.Card;

import java.util.ArrayList;
import java.util.Map;

public class ShopClient {
    public Map<Card, Integer> searchedCards;
    private String token = AccountClient.user.loginToken;

    public Response search(String searchedContent, String type) {

        ServerConnection serverConnection = new ServerConnection("/shop/search");
        serverConnection.parameters.put("token", token);
        if (!searchedContent.equals("")) serverConnection.parameters.put("search", searchedContent);
        if (!type.equals("")) serverConnection.parameters.put("type", type);
        Response response = serverConnection.getResponse();
        if (response.OK) {
            searchedCards = (Map<Card, Integer>) response.data;
        }
        return response;
    }

    public Response buy(String cardName) {
        ServerConnection serverConnection = new ServerConnection("/shop/buy");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("card_name", cardName);
        return serverConnection.getResponse();
    }

    public Response sell(String cardName) {
        ServerConnection serverConnection = new ServerConnection("/shop/sell");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("card_name", cardName);
        return serverConnection.getResponse();
    }

}
