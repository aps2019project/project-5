package server.models;

import server.models.http.HttpRequest;

import java.util.function.Consumer;

public class URL {
    public String urlPattern;
    public Consumer<? extends HttpRequest> viewFunction;

    public URL(String urlPattern, Consumer<? extends HttpRequest> viewFunction) {
        this.urlPattern = urlPattern;
        this.viewFunction = viewFunction;
    }
}
