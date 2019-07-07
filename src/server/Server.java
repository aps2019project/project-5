package server;


import server.controllers.*;
import server.models.Application;
import server.models.URL;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Server {
    private static final String DEFAULT_MAIN_PORT = "8000";
    private static final String DEFAULT_ADMIN_PORT = "8888";

    private static Properties getProperties() {
        try {
            FileInputStream input = new FileInputStream("src/server/config.properties");
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) { e.printStackTrace(); }

        Properties properties = new Properties();
        properties.setProperty("main_application.port", DEFAULT_MAIN_PORT);
        properties.setProperty("admin_application.port", DEFAULT_ADMIN_PORT);

        return properties;
    }

    private static void startMain() {
        Application main = new Application();
        main.port = Integer.valueOf(getProperties().getProperty("main_application.port"));
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

        main.urls.add(new URL("/cheat-mode/increment-drake", CheatModeController::incrementDrake, true));

        main.start();
    }

    private static void startAdmin() {
        Application adminApp = new Application();
        adminApp.port = Integer.valueOf(getProperties().getProperty("admin_application.port"));
        adminApp.urls.add(new URL("/server/online_users", AdminController::getOnlineUsers, false));
        adminApp.urls.add(new URL("/server/shop", AdminController::getShopCards, false));
        adminApp.start();
    }

    public static void main(String[] args) {
        startMain();
        startAdmin();
    }
}
