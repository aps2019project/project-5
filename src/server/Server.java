package server;


import server.controllers.Authentication;
import server.controllers.Shop;
import server.controllers.Collection;
import server.models.Application;
import server.models.URL;
import server.models.http.HttpResponseText;

public class Server {
    public static void main(String[] args) {
        Application authentication = new Application();
        authentication.port = 10000;
        authentication.urls.add(new URL("/login", Authentication::login));
        authentication.urls.add(new URL("/sign_up", Authentication::signUp));
        authentication.urls.add(new URL("/test", (request) -> new HttpResponseText("salam")));
        authentication.urls.add(new URL("/shop/search", Shop::searchShopCards));
        authentication.urls.add(new URL("/shop/buy", Shop::buy));
        authentication.urls.add(new URL("/shop/sell", Shop::sell));
        authentication.urls.add(new URL("/collection/search", Collection::searchCollectionCards));
        authentication.start();
    }
}
