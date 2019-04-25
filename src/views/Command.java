package views;

import java.util.regex.Pattern;

public class Command {
    private Pattern pattern;
    private String functionName;

    public Command(String pattern, String functionName) {
        this.functionName = functionName;
        this.pattern = Pattern.compile(pattern);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getFunctionName() {
        return functionName;
    }
}
