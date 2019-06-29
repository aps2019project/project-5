package models;

public class Response {
    public boolean OK;
    public String message;
    public Object data;

    public Response(boolean OK, String message, Object data) {
        this.OK = OK;
        this.message = message;
        this.data = data;
    }

    public Response(boolean OK, String message) {
        this.OK = OK;
        this.message = message;
        this.data = null;
    }
}
