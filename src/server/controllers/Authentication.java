package server.controllers;

import models.Account;
import server.models.Application;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseText;
import java.util.HashMap;
import java.util.Map;

public class Authentication extends Application {
    public Map<String, Account> users = new HashMap<>();

    public static HttpResponse login(HttpRequest request) {
        return new HttpResponseText("login");
    }

    public static HttpResponse signUp(HttpRequest request) {
        return new HttpResponseText("signUp");
    }
}
