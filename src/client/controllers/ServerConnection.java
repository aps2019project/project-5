package client.controllers;

import com.gilecode.yagson.YaGson;
import models.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class ServerConnection {
    private static final String DEFAULT_MAIN_PORT = "8000";
    private static final String DEFAULT_MAIN_HOST = "localhost";

    private static Properties getProperties() {
        try {
            FileInputStream input = new FileInputStream("src/client/config.properties");
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) { e.printStackTrace(); }

        Properties properties = new Properties();
        properties.setProperty("api.port", DEFAULT_MAIN_PORT);
        properties.setProperty("api.host", DEFAULT_MAIN_HOST);

        return properties;
    }


    public static final int port = Integer.valueOf(getProperties().getProperty("api.port"));
    public static final String host = getProperties().getProperty("api.host");
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
