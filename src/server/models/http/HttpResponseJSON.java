package server.models.http;

public class HttpResponseJSON extends HttpResponse {
    public static final String CONTENT_TYPE = "application/json";

    public HttpResponseJSON(int status, String statusMessage, String text) {
        super(status, statusMessage, CONTENT_TYPE, text);
    }

    public HttpResponseJSON(String text) {
        super(CONTENT_TYPE, text);
    }
}
