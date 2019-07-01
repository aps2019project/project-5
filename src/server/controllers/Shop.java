package server.controllers;

import com.gilecode.yagson.YaGson;
import models.Response;
import models.cards.Card;
import models.cards.Collection;
import server.data.DataReader;
import server.data.DataWriter;
import server.data.Files;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;


public class Shop {
    private static YaGson yaGson = new YaGson();
    public static Collection shop;

    static {
        shop = DataReader.getShopCollection();
    }

    public static HttpResponse searchShopCards(HttpRequest request) {
        String searchedContent = request.GET.get("search");
        if (searchedContent == null)
            searchedContent = "";
        String cardType = "Card";
        if (request.GET.containsKey("type")) {
            cardType = request.GET.get("type");
        }
        Class cardClass = null;
        try {
            cardClass = Class.forName("models.cards." + cardType);
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
        }
        Response response;
        if (searchedContent.equals("")) {
            response = new Response(true, "shop cards sent!", shop);
        } else {
            response = new Response(true, String.format("search result of %s was sent", searchedContent),
                    shop.filter(cardClass, searchedContent));
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse buy(HttpRequest request) {
        String name = request.GET.get("name");
        Card card;
        Response response;
        if (shop.searchCardByName(name) == null) {
            response = new Response(false, "card not found");
        } else {
            response = getCardTransfer(request, name, shop, request.user.cards);
        }
        DataWriter.saveData(Files.CARD_DATA, shop);
        DataWriter.saveData(Files.USER_DATA, Authentication.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse sell(HttpRequest request) {
        String name = request.GET.get("name");
        Card card;
        Collection cardCollection = request.user.cards;
        Response response;
        if (cardCollection.searchCardByName(name) == null) {
            response = new Response(false, "card not found");
        } else {
            response = getCardTransfer(request, name, cardCollection, shop);
        }
        DataWriter.saveData(Files.CARD_DATA, shop);
        DataWriter.saveData(Files.USER_DATA, Authentication.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    private static Response getCardTransfer(HttpRequest request, String name, Collection beginCollection, Collection endCollection) {
        Card card;
        Response response;
        card = beginCollection.searchCardByName(name);
        if (!beginCollection.decrease(card)) {
            response = new Response(false, "card not found in collection!");
        } else {
            if (beginCollection == request.user.cards) {
                request.user.drake += card.price;
            } else request.user.drake -= card.price;
            beginCollection.decrease(card);
            endCollection.add(card);
            response = new Response(true, "card added");
        }
        return response;
    }

}