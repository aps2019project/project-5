package client.controllers;

import models.Response;
import models.cards.Card;
import java.util.Map;

public class ShopClient {
    public static Map<Card, Integer> searchedCards;

    public static Response search(String token, String searchedContent, String type) {
        ServerConnection serverConnection = new ServerConnection("/shop/search");
        serverConnection.parameters.put("token", token);
        if (!searchedContent.equals("")) serverConnection.parameters.put("search", searchedContent);
        if (!type.equals("")) serverConnection.parameters.put("type", type);
        Response response = serverConnection.getResponse();
        if (response.OK)
            searchedCards = (Map<Card, Integer>) response.data;
        return response;
    }

    public static Response buy(String token, String cardName) {
        ServerConnection serverConnection = new ServerConnection("/shop/buy");
        serverConnection.parameters.put("token", token);
        serverConnection.parameters.put("card_name", cardName);
        return serverConnection.getResponse();
    }

    public static Response sell(String cardName) {
        ServerConnection serverConnection = new ServerConnection("/shop/sell");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("card_name", cardName);
        return serverConnection.getResponse();
    }

    public static int getDrakes() {
        ServerConnection serverConnection = new ServerConnection("/shop/get_drakes");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        int drakes = AccountClient.user.drake;
        Response response = serverConnection.getResponse();
        if(response.OK)
            drakes = (int) response.data;
        return drakes;
    }

}
