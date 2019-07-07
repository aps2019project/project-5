package server.controllers;

import models.Response;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

public class CheatModeController {

    public static HttpResponse incrementDrake(HttpRequest request) {
        request.user.drake += 10000000;
        Response response = new Response(true, "drake incremented", null);
        return new HttpResponseJSON(response);
    }
}
