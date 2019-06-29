package server.models.http;

import models.match.Match;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private static enum Methods {
        //        GET(Pattern.compile("")),
//        POST(Pattern.compile()),
//        HEADERS(Pattern.compile()),
        HEADER(Pattern.compile("(?<header>/([\\w]+)+)?")),
        VALUES(Pattern.compile("(?<key>[.+])\\s*:\\s*(?<value>[.+])")),
        VERSION(Pattern.compile("HTTP/(?<version>.+)")),
        METHOD(Pattern.compile("(?<method>[\\w]+)"));
        Pattern pattern;

        Methods(Pattern pattern) {
            this.pattern = pattern;
        }

        public Pattern getPattern() {
            return this.pattern;
        }
    }

    public String version;
    public String method;
    public Map<String, String> GET = new HashMap<>();
    public Map<String, String> POST = new HashMap<>();
    public Map<String, String> headers = new HashMap<>();
    public String header;

    public HttpRequest(String requestText) {
        // TODO: parse request text to HttpRequest Object
        String[] lines = requestText.split("\\n");
        Matcher matcher = Methods.METHOD.getPattern().matcher(lines[0]);
        if (matcher.find()) {
            this.method = matcher.group("method");
        }
        matcher = Methods.VERSION.getPattern().matcher(lines[0]);
        if (matcher.find()) {
            this.version = matcher.group("version");
        }
        matcher = Methods.HEADER.getPattern().matcher(lines[0]);
        if (matcher.find()) {
            this.header = matcher.group("header");
        }

        for (String line : lines) {
            matcher = Methods.VALUES.getPattern().matcher(line);
            if (matcher.find()) {
                switch (method) {
                    case "GET":
                        this.GET.put(matcher.group("key"), matcher.group("value"));
                        break;

                    case "POST":
                        this.POST.put(matcher.group("key"), matcher.group("value"));

                        break;

                    case "HEAD":
                        this.headers.put(matcher.group("key"), matcher.group("value"));

                        break;
                }
            }

        }
    }
}
