package server;


import server.controllers.Authentication;
import server.controllers.Collection;
import server.models.Application;
import server.models.URL;

public class Server {
    public static void main(String[] args) {
        Application authentication = new Application();
        authentication.port = 10000;
        authentication.urls.add(new URL("/login", Authentication::login));
        authentication.urls.add(new URL("/sign_up", Authentication::signUp));
        authentication.urls.add(new URL("/shop_getCards", Authentication::getShopCollection));
        authentication.urls.add(new URL("/shop_searchCard", Authentication::searchShopCards));
        authentication.urls.add(new URL("/collection_search_Card", Collection::searchCollectionCards));
        authentication.start();


    }
}
