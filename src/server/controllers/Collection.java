package server.controllers;

import models.Response;
import models.cards.*;
import server.data.DataReader;
import server.models.Application;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

import static server.data.DataWriter.yaGson;

public class Collection extends Application {

    public static Class getCardClass(String className) {
        Class cardClass = Card.class;
        if(className == null) className = "";
        if(className.equalsIgnoreCase("minion"))
            cardClass = Minion.class;
        if(className.equalsIgnoreCase("spell"))
            cardClass = Spell.class;
        if(className.equalsIgnoreCase("hero"))
            cardClass = Hero.class;
        return cardClass;
    }

    public static HttpResponse searchCollectionCards(HttpRequest request) {
        String searchedContent = request.GET.get("search");
        Class cardClass = getCardClass(request.GET.get("type"));

        Response response;
        response = new Response(true, String.format("search result of %s is sent", searchedContent),
                request.user.cards.filter(cardClass, searchedContent));
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse addDeck(HttpRequest request) {
        Response response;
        String deckName = request.GET.get("name");
        if(deckName == null)
            response = new Response(false, "name parameter not sent", 100);
        else {
            Deck deck = request.user.decks.get(deckName);
            if(deck != null) {
                response = new Response(false, "deck exists.", 101);
            } else {
                deck = new Deck(deckName);
                request.user.decks.put(deckName, deck);
                response = new Response(true, "deck added", deck);
            }
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }


}
