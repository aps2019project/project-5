package server.controllers;

import com.gilecode.yagson.YaGson;
import models.Response;
import models.cards.Card;
import models.cards.Collection;
import server.data.DataReader;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

public class Shop {
    private static YaGson yaGson = new YaGson();


    public static HttpResponse searchShopCards(HttpRequest request) {
        String searchedContent = request.GET.get("search");
        String cardType = "Card";
        if (request.GET.containsKey("type")) {
            cardType = request.GET.get("type");
        }
        Class cardClass = null;
        try {
            cardClass = Class.forName(cardType);
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
        }
        Response response = null;
        if (request.user == null) {
            response = new Response(false, "You are not logged in!");
        } else if (searchedContent.equals("")) {
            response = new Response(true, "shop cards sent!", DataReader.getShopCollection().cards);
        } else {
            response = new Response(true, String.format("search result of %s is sent", searchedContent),
                    DataReader.getShopCollection().filter(cardClass, searchedContent));
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse buy(HttpRequest request) {
        String name = request.GET.get("name");
        Collection shopCollection = DataReader.getShopCollection();
        Card card;
        Response response = null;
        if (request.user == null) {
            response = new Response(false, "You are not logged in!");
        } else if (shopCollection.searchCardByName(name) == null) {
            response = new Response(false, "card not found");
        } else {
            response = getCardTransfer(request, name, shopCollection);
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse sell(HttpRequest request) {
        String name = request.GET.get("name");
        Card card;
        Collection cardCollection;
        Response response = null;
        if (request.user == null) {
            response = new Response(false, "You are not logged in!");
        } else if ((cardCollection = request.user.cardCollection).searchCardByName(name) == null) {
            response = new Response(false, "card not found");
        } else {
            response = getCardTransfer(request, name, cardCollection);
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    private static Response getCardTransfer(HttpRequest request, String name, Collection cardCollection) {
        Card card;
        Response response;
        card = cardCollection.searchCardByName(name);
        cardCollection.decrease(card);
        if (!cardCollection.decrease(card)) {
            response = new Response(false, "card not found in collection!");
        } else {
            request.user.drake -= card.price;
            request.user.cardCollection.add(card);
            response = new Response(true, "card added");
        }
        return response;
    }

}