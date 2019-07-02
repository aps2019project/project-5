package server.controllers;

import models.Response;
import models.cards.*;
import server.data.DataWriter;
import server.data.Files;
import server.models.Application;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;
import static server.data.DataWriter.yaGson;

public class CollectionController extends Application {

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
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse addCardToDeck(HttpRequest request) {
        Response response;
        String deckName = request.GET.get("deck_name");
        String cardName = request.GET.get("card_name");
        if(deckName == null)
            response = new Response(false, "deck_name not sent", 100);
        else if(cardName == null)
            response = new Response(false, "card_name not sent", 100);
        else {
            Deck deck = request.user.decks.get(deckName);
            Card card = request.user.cards.searchCardByName(cardName);
            if(deck == null)
                response = new Response(false, "deck not found with this name", 102);
            else if(card == null)
                response = new Response(false, "card not found with this name", 103);
            else if (request.user.cards.count(card) <= deck.count(card))
                response = new Response(false, "this card is not enough", 104);
            else if(deck.add(card))
                response = new Response(true, "card added to deck", deck);
            else
                response = new Response(false, "can't add card to deck", 105);
        }
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse setMainDeck(HttpRequest request) {
        Response response;
        String deckName = request.GET.get("deck_name");
        if(deckName == null)
            response = new Response(false, "deck_name not sent", 100);
        else {
            Deck deck = request.user.decks.get(deckName);
            if(deck == null)
                response = new Response(false, "deck with this name not found", 106);
            else {
                if(!deck.isValid()) {
                    response = new Response(false, "selected deck is not valid :(");
                } else {
                    request.user.mainDeck = deck;
                    response = new Response(true, "deck selected successfully :)", deck);
                }
            }
        }
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse getDeck(HttpRequest request) {
        Response response;
        String deckName = request.GET.get("deck_name");
        if(deckName == null)
            response = new Response(false, "deck_name not sent", 100);
        else {
            Deck deck = request.user.decks.get(deckName);
            if(deck == null)
                response = new Response(false, "deck with this name not found", 106);
            else {
                response = new Response(true, "see the deck!!!", deck);
            }
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse removeDeck(HttpRequest request) {
        Response response;
        String deckName = request.GET.get("deck_name");
        if(deckName == null)
            response = new Response(false, "deck_name not sent", 100);
        else {
            Deck deck = request.user.decks.get(deckName);
            if(deck == null)
                response = new Response(false, "deck with this name not found", 106);
            else {
                request.user.decks.remove(deck.name);
                response = new Response(true, "deck deleted!", request.user);
            }
        }
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse isValid(HttpRequest request) {
        Response response;
        String deckName = request.GET.get("deck_name");
        if(deckName == null)
            response = new Response(false, "deck_name not sent", 100);
        else {
            Deck deck = request.user.decks.get(deckName);
            if(deck == null)
                response = new Response(false, "deck with this name not found", 106);
            else {
                boolean isValid = deck.isValid();
                response = new Response(true, "see is deck is valid or not!", isValid);
            }
        }
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }


    public static HttpResponse removeCardFromDeck(HttpRequest request) {
        Response response;
        String deckName = request.GET.get("deck_name");
        String cardName = request.GET.get("card_name");
        if(deckName == null)
            response = new Response(false, "deck_name not sent", 100);
        else if(cardName == null)
            response = new Response(false, "card_name not sent", 100);
        else {
            Deck deck = request.user.decks.get(deckName);
            if(deck == null)
                response = new Response(false, "deck with this name not found", 106);
            else {
                Card card = deck.searchCardByName(cardName);
                if(card == null)
                    response = new Response(false, "card not found in this deck", 107);
                else {
                    deck.decrease(card);
                    response = new Response(false, "card removed from the deck", deck);
                }
            }
        }
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        return new HttpResponseJSON(yaGson.toJson(response));
    }

}
