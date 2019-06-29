package server.models.http;

public class HttpResponse {
    public final String version = "HTTP/1.1";
    public int status;
    public String statusMessage;
    public String contentType;
    public String body;

    public HttpResponse(int status, String statusMessage, String contentType, String body) {
        this.status = status;
        this.statusMessage = statusMessage;
        this.contentType = contentType;
        this.body = body;
    }

    public HttpResponse(String contentType, String body) {
        this.status = 200;
        this.statusMessage = "OK";
        this.contentType = contentType;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("%s %d %s\n", version, status, statusMessage) +
                String.format("Content-Type: %s\n\n", contentType) +
                body;
    }
}
