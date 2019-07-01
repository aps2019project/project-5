package server;


import com.gilecode.yagson.YaGson;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import models.Account;
import models.Response;
import server.controllers.Authentication;
import server.controllers.Shop;
import server.controllers.Collection;
import server.models.Application;
import server.models.URL;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

import java.nio.channels.AcceptPendingException;

public class Server {
    public static void main(String[] args) {
        Application mainApplication = new Application();
        mainApplication.port = 10000;
        mainApplication.urls.add(new URL("/login", Authentication::login, false));
        mainApplication.urls.add(new URL("/sign_up", Authentication::signUp, false));
        mainApplication.urls.add(new URL("/shop/search", Shop::searchShopCards, true));
        mainApplication.urls.add(new URL("/shop/buy", Shop::buy, true));
        mainApplication.urls.add(new URL("/shop/sell", Shop::sell, true));
        mainApplication.urls.add(new URL("/collection/search", Collection::searchCollectionCards, true));

        Authentication.login(new HttpRequest("GET /login?username=mahdi&password=mahdi HTTP/1.1"));



        mainApplication.urls.add(new URL("/collection/search", Collection::searchCollectionCards, true));
        mainApplication.start();
    }
}
