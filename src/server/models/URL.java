package server.models;

import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import java.util.function.Function;

public class URL {
    public String urlPattern;
    public Function<HttpRequest, HttpResponse> viewFunction;

    public URL(String urlPattern, Function<HttpRequest, HttpResponse> viewFunction) {
        this.urlPattern = urlPattern;
        this.viewFunction = viewFunction;
    }

    public boolean matches(String url) {
        // TODO implement

        return false;
    }
}
