package server.controllers;

import com.gilecode.yagson.YaGson;
import models.Account;
import models.Response;
import server.data.DataReader;
import server.data.DataWriter;
import server.data.Files;
import server.models.Application;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;
import sun.misc.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Authentication extends Application {
    private static YaGson yaGson = new YaGson();
    public static Map<String, Account> users;
    private static HashMap<String, Account> connectedAccounts = new HashMap<>();

    public static String randomString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            stringBuilder.append(alphaNumericString.charAt(random.nextInt(alphaNumericString.length())));
        }
        return stringBuilder.toString();
    }


    public static HttpResponse login(HttpRequest request) {
        String username = request.GET.get("username");
        String password = request.GET.get("password");
        Response response;
        users = DataReader.readAccounts();
        if (!users.containsKey(username)) {
            response = new Response(false, "Username is not valid!");
        } else if (!users.get(username).password.equals(password)) {
            response = new Response(false, "Password is not true!");
        } else {
            Account account = users.get(username);
            String token = randomString(30);
            account.loginToken = token;
            connectedAccounts.put(token, account);
            response = new Response(true, "you logged in!", account);
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }

    public static HttpResponse signUp(HttpRequest request) {
        users = DataReader.readAccounts();
        String username = request.GET.get("username");
        String password = request.GET.get("password");
        Response response;
        if (username == null || username.equals("")) {
            response = new Response(false, "Username can't be null.");
        } else if (password == null || password.equals("")) {
            response = new Response(false, "Password can't be null.");
        } else if (users.containsKey(username)) {
            response = new Response(false, "This username exists.");
        } else {
            Account account = new Account(username, password);
            users.put(username, account);
            DataWriter.saveData(Files.USER_DATA, users);
            response = new Response(true, "User created.", account);
        }
        return new HttpResponseJSON(yaGson.toJson(response));
    }
}
