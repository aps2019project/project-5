package server.models.http;

import com.gilecode.yagson.YaGson;
import models.Response;

public class HttpResponseJSON extends HttpResponse {
    public static final String CONTENT_TYPE = "application/json";
    private static YaGson yaGson = new YaGson();

    public HttpResponseJSON(int status, String statusMessage, String text) {
        super(status, statusMessage, CONTENT_TYPE, text);
    }

    public HttpResponseJSON(int status, String statusMessage, Response response) {
        super(status, statusMessage, CONTENT_TYPE, yaGson.toJson(response));
    }

    public HttpResponseJSON(String text) {
        super(CONTENT_TYPE, text);
    }

    public HttpResponseJSON(Response response) {
        super(CONTENT_TYPE, yaGson.toJson(response));
    }
}
