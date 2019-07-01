package server.models.http;

import models.Account;
import server.controllers.Authentication;
import sun.plugin2.message.GetAppletMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private enum Methods {
        VALUES(Pattern.compile("(?<key>.+)\\s*:\\s*(?<value>.+)")),
        PARAMETERS(Pattern.compile("(?<key>[^\\W&?]+)=(?<value>[^\\W&?]+)")),
        URLS(Pattern.compile("(?<url>\\/.+)\\?")),
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
    public Map<String, String> GET;
    public Map<String, String> POST;
    public Account user = null;
    public Map<String, String> headers = new HashMap<>();

    public HttpRequest(String requestText) {
        String[] lines = requestText.split("\\n");
        Matcher matcher = Methods.FIRST_LINE.getPattern().matcher(lines[0]);
        if (matcher.find()) {
            this.method = matcher.group("method");
            if (this.method.equals("POST"))
                POST = new HashMap<>();
            if (this.method.equals("GET"))
                GET = new HashMap<>();
            this.version = matcher.group("version");
            url = matcher.group("url");
        }
        if (url != null) {
            matcher = Methods.PARAMETERS.pattern.matcher(url);
            while (matcher.find()) {
                if (this.method.equals("POST")) {
                    POST.put(matcher.group("key"), matcher.group("value"));
                }
                if (this.method.equals("GET")) {
                    GET.put(matcher.group("key"), matcher.group("value"));
                }
            }

            matcher = Methods.URLS.pattern.matcher(url);
            if (matcher.find()) {
                url = matcher.group("url");
            }
        }

        for (String line : lines) {
            if (line.equals(lines[0])) continue;
            matcher = Methods.VALUES.pattern.matcher(line);
            if (matcher.find()) {
                this.headers.put(matcher.group("key"), matcher.group("value"));
            }
        }

        if(GET != null) {
            System.out.println("request is get: " + url);
            if(GET.get("token") != null) {
                System.out.println("token was sent: " + GET.get("token"));
                Account account = Authentication.connectedAccounts.get(GET.get("token"));
                if(account != null) {
                    user = account;
                    System.out.println("User logged in: " + account.username);
                }
            }
        }
    }
}
