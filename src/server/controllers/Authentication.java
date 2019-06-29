package server.controllers;

import models.Account;
import server.data.DataReader;
import server.models.Application;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseText;
import java.util.Map;

public class Authentication extends Application {
    public static Map<String, Account> users;

    static {
        users = DataReader.readAccounts();
    }

    public static HttpResponse login(HttpRequest request) {
        return new HttpResponseText("login");
    }

    public static HttpResponse signUp(HttpRequest request) {
        return new HttpResponseText("signUp");
    }
}
