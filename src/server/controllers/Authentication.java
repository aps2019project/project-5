package server.controllers;

import com.gilecode.yagson.YaGson;
import models.Account;
import models.Response;
import server.data.DataReader;
import server.models.Application;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;
import server.models.http.HttpResponseText;

import java.util.Map;

public class Authentication extends Application {
    public static YaGson yaGson = new YaGson();
    public static Map<String, Account> users;

    static {
        users = DataReader.readAccounts();
    }

    public static HttpResponse login(HttpRequest request) {
        return new HttpResponseText("login");
    }

    public static HttpResponse signUp(HttpRequest request) {
        String username = request.GET.get("username");
        String password = request.GET.get("password");
        Response response;
        if(username == null || username.equals("")) {
            response = new Response(false, "Username can't be null.");
        } else if(password == null || password.equals("")) {
            response = new Response(false, "Password can't be null.");
        } else if(users.containsKey(username)) {
            response = new Response(false, "This username exists.");
        } else {
            Account account = new Account(username, password);
            users.put(username, account);
            response = new Response(true, "User created.", account);
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }
}
