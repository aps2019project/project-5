package server;


import server.controllers.Authentication;
import server.controllers.Shop;
import server.controllers.Collection;
import server.models.Application;
import server.models.URL;

public class Server {
    public static void main(String[] args) {
        Application authentication = new Application();
        authentication.port = 80;
        authentication.urls.add(new URL("/login", Authentication::login, false));
        authentication.urls.add(new URL("/sign_up", Authentication::signUp, false));
        authentication.urls.add(new URL("/shop/search", Shop::searchShopCards, true));
        authentication.urls.add(new URL("/shop/buy", Shop::buy, true));
        authentication.urls.add(new URL("/shop/sell", Shop::sell, true));
        authentication.urls.add(new URL("/collection/search", Collection::searchCollectionCards, true));
        authentication.urls.add(new URL("/collection/create_deck", Collection::addDeck, true));
        authentication.urls.add(new URL("/collection/add_card_to_deck", Collection::addCardToDeck, true));
        authentication.urls.add(new URL("/collection/get_deck", Collection::getDeck, true));
        authentication.urls.add(new URL("/collection/remove_card_from_deck", Collection::removeCardFromDeck, true));
        authentication.start();
    }
}
