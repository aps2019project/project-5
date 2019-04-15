package views;

import java.util.regex.Pattern;

public class Command {
    private Pattern pattern;
    private String functionName;

    public Command(String patternString, String functionName) {
        this.pattern = Pattern.compile(patternString);
        this.functionName = functionName;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getFunctionName() {
        return functionName;
    }
}
