package server.models;

import server.models.http.HttpRequest;
import server.models.http.HttpResponse;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URL {
    public String urlPattern;
    public Function<HttpRequest, HttpResponse> viewFunction;

    public URL(String urlPattern, Function<HttpRequest, HttpResponse> viewFunction) {
        this.urlPattern = urlPattern;
        this.viewFunction = viewFunction;
    }

    public boolean matches(String url) {
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }
}
