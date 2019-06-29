package server.models.http;

import models.match.Match;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private static enum Methods {
        VALUES(Pattern.compile("(?<key>.+)\\s*:\\s*(?<value>.+)")),
        FIRST_LINE(Pattern.compile("^(?<method>GET|POST)\\s+(?<url>.+(\\?.*)?)\\s+HTTP\\/(?<version>\\d+\\.\\d+)$"));
        Pattern pattern;

        Methods(Pattern pattern) {
            this.pattern = pattern;
        }

        public Pattern getPattern() {
            return this.pattern;
        }
    }

    public String version;
    public String url;
    public String method;
    public Map<String, String> GET = new HashMap<>();
    public Map<String, String> POST = new HashMap<>();
    public Map<String, String> headers = new HashMap<>();

    public HttpRequest(String requestText) {
        System.out.println(requestText);

        String[] lines = requestText.split("\\n");
        Matcher matcher = Methods.FIRST_LINE.getPattern().matcher(lines[0]);
        if (matcher.find()) {
            this.method = matcher.group("method");
            this.version = matcher.group("version");
            url = matcher.group("url");
        }
        System.out.println(url);
        System.out.println(method);
        System.out.println(version);


        for (String line : lines) {
            if(line.equals(lines[0])) continue;
            matcher = Methods.VALUES.pattern.matcher(line);
            if (matcher.find())
                this.headers.put(matcher.group("key"), matcher.group("value"));

        }
    }
}
