package server.controllers;

import server.models.Application;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseText;

public class Authentication extends Application {
    public static HttpResponse login(HttpRequest request) {
        return new HttpResponseText("login");
    }

    public static HttpResponse signUp(HttpRequest request) {
        return new HttpResponseText("signUp");
    }
}
