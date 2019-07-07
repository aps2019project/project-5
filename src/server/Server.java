package server;


import server.controllers.*;
import server.models.Application;
import server.models.URL;

public class Server {
    public static void main(String[] args) {
        Application main = new Application();
        main.port = 80;
        main.urls.add(new URL("/login", AuthenticationController::login, false));
        main.urls.add(new URL("/sign_up", AuthenticationController::signUp, false));
        main.urls.add(new URL("/profile", AuthenticationController::profile, true));

        main.urls.add(new URL("/shop/search", ShopController::searchShopCards, true));
        main.urls.add(new URL("/shop/buy", ShopController::buy, true));
        main.urls.add(new URL("/shop/sell", ShopController::sell, true));

        main.urls.add(new URL("/collection/search", CollectionController::searchCollectionCards, true));
        main.urls.add(new URL("/collection/create_deck", CollectionController::addDeck, true));
        main.urls.add(new URL("/collection/add_card_to_deck", CollectionController::addCardToDeck, true));
        main.urls.add(new URL("/collection/get_deck", CollectionController::getDeck, true));
        main.urls.add(new URL("/collection/remove_deck", CollectionController::removeDeck, true));
        main.urls.add(new URL("/collection/remove_card_from_deck", CollectionController::removeCardFromDeck, true));
        main.urls.add(new URL("/collection/is_valid", CollectionController::isValid, true));
        main.urls.add(new URL("/collection/set_main_deck", CollectionController::setMainDeck, true));
        main.urls.add(new URL("/collection/get_all_decks", CollectionController::getDecks, true));
        main.urls.add(new URL("/collection/get_main_deck", CollectionController::getMainDeck, true));

        main.urls.add(new URL("/battle/request", BattleController::battleRequest, true));
        main.urls.add(new URL("/battle/cancel_request", BattleController::cancelBattleRequest, true));
        main.urls.add(new URL("/battle/select_card", BattleController::selectCard, true));
        main.urls.add(new URL("/battle/get_match", BattleController::getMatch, true));
        main.urls.add(new URL("/battle/insert", BattleController::insert, true));
        main.urls.add(new URL("/battle/end_turn", BattleController::endTurn, true));
        main.urls.add(new URL("/battle/move", BattleController::moveCard, true));
        main.urls.add(new URL("/battle/attack", BattleController::attack, true));

        main.urls.add(new URL("/chat/send_message", ChatController::sendMessage, true));
        main.urls.add(new URL("/chat/update", ChatController::update, true));
        main.start();
    }
}
