package server.controllers;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.Response;
import models.cards.Card;
import models.cards.Collection;
import server.data.DataReader;
import server.data.DataWriter;
import server.data.Files;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;


public class ShopController {
    private static YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public static Collection shop;

    static {
        shop = DataReader.getShopCollection();
    }

    public static HttpResponse searchShopCards(HttpRequest request) {
        String searchedContent = request.GET.get("search");
        if (searchedContent == null)
            searchedContent = "";
        Class cardClass = CollectionController.getCardClass(request.GET.get("type"));
        Response response;
        if (searchedContent.equals("") && cardClass == Card.class) {
            response = new Response(true, "shop cards sent!", shop);
        } else {
            response = new Response(true, String.format("search result of %s was sent", searchedContent),
                    shop.filter(cardClass, searchedContent));
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse buy(HttpRequest request) {
        String cardName = request.GET.get("card_name");
        Response response;
        if (cardName == null)
            response = new Response(false, "card_name not sent", 100);
        else {
            Card card = shop.searchCardByName(cardName);
            if (card == null)
                response = new Response(false, "this card not found in shop", 120);
            else {
                if (request.user.drake < card.price)
                    response = new Response(false, "you have not enough drakes.", 121);
                else if (shop.decrease(card)) {
                    request.user.cards.add(card);
                    request.user.drake -= card.price;
                    response = new Response(true, "card transfer completed.", request.user);
                } else {
                    response = new Response(false, "card not enough in shop", 122);
                }
            }
        }
        DataWriter.saveData(Files.CARD_DATA, shop);
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse sell(HttpRequest request) {
        String cardName = request.GET.get("card_name");
        Response response;
        if (cardName == null)
            response = new Response(false, "card_name not sent", 100);
        else {
            Card card = request.user.cards.searchCardByName(cardName);
            if (card == null)
                response = new Response(false, "you don't have this card in your collection", 120);
            else {
                request.user.cards.decrease(card);
                shop.add(card);
                request.user.drake += card.price;
                response = new Response(true, "card sells.", request.user);
            }
        }
        DataWriter.saveData(Files.CARD_DATA, shop);
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

}