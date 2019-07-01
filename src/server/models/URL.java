package server.models;

import server.models.http.HttpRequest;
import server.models.http.HttpResponse;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URL {
    private String urlPattern;
    public Function<HttpRequest, HttpResponse> viewFunction;
    public boolean loginRequired;

    public URL(String urlPattern, Function<HttpRequest, HttpResponse> viewFunction, boolean loginRequired) {
        this.urlPattern = urlPattern;
        this.viewFunction = viewFunction;
        this.loginRequired = loginRequired;
    }

    public boolean matches(String url) {
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }
}
