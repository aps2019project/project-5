package server.controllers;

import models.Response;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

import java.util.ArrayList;
import java.util.stream.Collector;

public class WatchController {
    public static HttpResponse getLiveMatches(HttpRequest request) {
        Response response = new Response(true, "token gotten.",
                new ArrayList<>(BattleController.playingMatches.values()));
        return new HttpResponseJSON(response);
    }
}
