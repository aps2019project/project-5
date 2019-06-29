package server.models.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public String version;
    public String method;
    public Map<String, String> GET = new HashMap<>();
    public Map<String, String> POST = new HashMap<>();
    public Map<String, String> headers = new HashMap<>();
}
