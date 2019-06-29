package server.models.http;

public class HttpResponseText extends HttpResponse {
    public static final String CONTENT_TYPE = "text.plain";

    public HttpResponseText(int status, String statusMessage, String text) {
        super(status, statusMessage, CONTENT_TYPE, text);
    }

    public HttpResponseText(String text) {
        super(CONTENT_TYPE, text);
    }
}
