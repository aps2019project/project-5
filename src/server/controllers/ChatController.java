package server.controllers;

import models.Response;
import models.chat.Chat;
import models.chat.Message;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

public class ChatController {
    public static Chat chat = new Chat();

    public static HttpResponse sendMessage(HttpRequest request) {
        String text = request.GET.get("text");
        Response response;
        if(text == null) {
            response = new Response(false, "text not sent");
        } else {
            chat.messages.add(new Message(text, request.user.username));
            response = new Response(true, "message sent.", chat);
        }
        return new HttpResponseJSON(response);
    }

    public static HttpResponse update(HttpRequest request) {
        Response response = new Response(true, "chat messages sent.", chat);
        return new HttpResponseJSON(response);
    }
}
