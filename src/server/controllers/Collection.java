package server.controllers;

import models.Account;
import models.Response;
import server.data.DataReader;
import server.data.DataWriter;
import server.data.Files;
import server.models.Application;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

import static server.data.DataWriter.yaGson;

public class Collection extends Application {

    public static HttpResponse searchCollectionCards(HttpRequest request) {
        String token = request.GET.get("token");
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

        Response response = new Response(true, String.format("search result of %s is sent", searchedContent),
                    Authentication.connectedAccounts.get(token).cards.filter(cardClass, searchedContent));

        return new HttpResponseJSON(yaGson.toJson(response));
    }


}
