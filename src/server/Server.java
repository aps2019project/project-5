package server;


import server.controllers.AuthenticationController;
import server.controllers.BattleController;
import server.controllers.ShopController;
import server.controllers.CollectionController;
import server.models.Application;
import server.models.URL;

public class Server {
    public static void main(String[] args) {
        Application authentication = new Application();
        authentication.port = 80;
        authentication.urls.add(new URL("/login", AuthenticationController::login, false));
        authentication.urls.add(new URL("/sign_up", AuthenticationController::signUp, false));
        authentication.urls.add(new URL("/profile", AuthenticationController::profile, true));

        authentication.urls.add(new URL("/shop/search", ShopController::searchShopCards, true));
        authentication.urls.add(new URL("/shop/buy", ShopController::buy, true));
        authentication.urls.add(new URL("/shop/sell", ShopController::sell, true));

        authentication.urls.add(new URL("/collection/search", CollectionController::searchCollectionCards, true));
        authentication.urls.add(new URL("/collection/create_deck", CollectionController::addDeck, true));
        authentication.urls.add(new URL("/collection/add_card_to_deck", CollectionController::addCardToDeck, true));
        authentication.urls.add(new URL("/collection/get_deck", CollectionController::getDeck, true));
        authentication.urls.add(new URL("/collection/remove_deck", CollectionController::removeDeck, true));
        authentication.urls.add(new URL("/collection/remove_card_from_deck", CollectionController::removeCardFromDeck, true));
        authentication.urls.add(new URL("/collection/is_valid", CollectionController::isValid, true));
        authentication.urls.add(new URL("/collection/set_main_deck", CollectionController::setMainDeck, true));

        authentication.urls.add(new URL("/battle/request", BattleController::battleRequest, true));
        authentication.urls.add(new URL("/battle/cancel_request", BattleController::cancelBattleRequest, true));
        authentication.urls.add(new URL("/battle/select_card", BattleController::selectCard, true));
        authentication.urls.add(new URL("/battle/get_match", BattleController::getMatch, true));
        authentication.urls.add(new URL("/battle/insert", BattleController::insert, true));
        authentication.urls.add(new URL("/battle/end_turn", BattleController::endTurn, true));
        authentication.start();
    }
}
