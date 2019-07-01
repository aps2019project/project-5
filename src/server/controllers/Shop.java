package server.controllers;

import com.gilecode.yagson.YaGson;
import models.Response;
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

//    public static HttpResponse getShopCollection(HttpRequest request) {
//        String token = request.GET.get("token");
//        Response response = null;
//        if (!users.containsKey(token)) {
//            response = new Response(false, "You are not logged in!");
//        } else {
//            response = new Response(true, "shop cards sent!", DataReader.getShopCollection().cards);
//        }
//        return new HttpResponseJSON(yaGson.toJson(response));
//    }
}
