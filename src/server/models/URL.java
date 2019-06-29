package server.models;

import java.util.regex.Pattern;

public class URL {
    public String urlPattern;
    public String functionName;

    public URL(String urlPattern, String functionName) {
        this.urlPattern = urlPattern;
        this.functionName = functionName;
    }
}
