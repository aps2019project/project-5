package client.controllers;

import client.models.Hand;
import com.gilecode.yagson.YaGson;
import models.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ServerConnection {
    public static final int port = 80;
    public static final String host = "localhost";
    public HashMap<String, String> parameters = new HashMap<>();
    public String route;

    public ServerConnection(String route) {
        this.route = route;
    }

    public URL getUrl() throws MalformedURLException {
        return new URL("http://" + host + ":" + port + route + "?" + getParameters());
    }

    public String getParameters() {
        ArrayList<String> res = new ArrayList<>();
        parameters.forEach((k, v) -> res.add(String.format("%s=%s", k, v)));

        return String.join("&", res);
    }

    public Response getResponse() {
        try {
            URL url = getUrl();
            HttpURLConnection urlConnection = ((HttpURLConnection) url.openConnection());
            urlConnection.connect();
            InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
            YaGson yaGson = new YaGson();
            return yaGson.fromJson(reader, Response.class);
        } catch (IOException e) {
            return new Response(false, "connection problem");
        }
    }
}
