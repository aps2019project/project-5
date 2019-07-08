package server.controllers;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.Response;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.Spell;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminController {
    private static YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public static HttpResponse getOnlineUsers(HttpRequest httpRequest) {
        String bodyFormat = "<html><head><style>td{background-color: white;} th{background-color: #eee;}</style><title>Online Users</title></head><body><a href='/admin'>back</a><h1>Online Users</h1><table border='1' style='background-color: gray;'><thead><tr><th></th><th>Username</th><th>token</th></tr></thead><tbody>%s</tbody></table></body></head></html>";
        StringBuilder rows = new StringBuilder();
        String rowFormat = "<tr><td>%s</td><td>%s</td><td>%s</td></tr>";
        AtomicInteger i = new AtomicInteger(1);
        AuthenticationController.connectedAccounts.forEach(((token, account) -> {
            rows.append(String.format(rowFormat, i.getAndIncrement(), account.username, token));
        }));
        return new HttpResponse("text/html", String.format(bodyFormat, rows.toString()));
    }

    public static HttpResponse getShopCards(HttpRequest httpRequest) {
        String bodyFormat = "<html><head><style>td{background-color: white;} th{background-color: #eee;}</style><title>Shop</title></head><body><a href='/admin'>back</a><h1>Shop</h1><table border='1' style='background-color: gray;'><thead><tr><th></th><th>Name</th><th>price</th><th>count</th><th>type</th><th>mana point</th></tr></thead><tbody>%s</tbody></table></body></head></html>";
        StringBuilder rows = new StringBuilder();
        String rowFormat = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
        AtomicInteger i = new AtomicInteger(1);
        ShopController.shop.cards.forEach(((card, count) -> {
            String type = "CARD";
            if(card instanceof Minion) type = "MINION";
            if(card instanceof Spell) type = "SPELL";
            if(card instanceof Hero) type = "HERO";
            rows.append(String.format(rowFormat, i.getAndIncrement(), card.name, card.price, count, type, card.manaPoint));
        }));
        return new HttpResponse("text/html", String.format(bodyFormat, rows.toString()));
    }

    public static HttpResponse adminPanel(HttpRequest request) {
        String html = "<html><head><title>Admin Panel</title></head><body><a href = '/admin/shop'>Shop</a><br/><a href='/admin/online_users'>Online Users</a></body></html>";
        return new HttpResponse("text/html", html);
    }
}
