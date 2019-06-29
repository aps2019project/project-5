package client.views;

import java.util.regex.Pattern;

public class Command {
    private Pattern pattern;
    private String functionName;
    private String sampleCommand;
    private String commandAction;
    private boolean hasHelp;

    public Command(String pattern, String functionName) {
        this.functionName = functionName;
        this.pattern = Pattern.compile(pattern);
        this.hasHelp = false;
    }

    public Command(String pattern, String functionName, String sampleCommand, String commandAction) {
        this(pattern, functionName);
        this.hasHelp = true;
        this.sampleCommand = sampleCommand;
        this.commandAction = commandAction;
    }

    public String getSampleCommand() {
        return sampleCommand;
    }

    public String getCommandAction() {
        return commandAction;
    }

    public boolean hasHelp() {
        return hasHelp;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getFunctionName() {
        return functionName;
    }
}
