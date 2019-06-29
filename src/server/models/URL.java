package server.models;

import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import java.util.function.Function;

public class URL {
    public String urlPattern;
    public Function<? extends HttpRequest, ? extends HttpResponse> viewFunction;

    public URL(String urlPattern, Function<? extends HttpRequest, ? extends HttpResponse> viewFunction) {
        this.urlPattern = urlPattern;
        this.viewFunction = viewFunction;
    }

    public boolean mathces(String url) {
        // TODO implement

        return true;
    }
}
