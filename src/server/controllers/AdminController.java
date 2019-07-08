package server.controllers;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.Account;
import models.Response;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

import java.util.ArrayList;

public class AdminController {
    private static YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public static HttpResponse getOnlineUsers(HttpRequest httpRequest) {
        ArrayList<String> connectedAccounts = new ArrayList<>();
        AuthenticationController.connectedAccounts.forEach((string, account) -> connectedAccounts.add(account.username));
        Response response = new Response(true, "online users are", connectedAccounts);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse getShopCards(HttpRequest httpRequest) {
        Response response = new Response(true, "cards", ShopController.shop.cards);
        return new HttpResponseJSON(yaGson.toJson(response));
    }
}
