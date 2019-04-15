package views;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Command {
    private Pattern pattern;
    private String functionName;
    public static Scanner scanner = new Scanner(System.in);
    public Pattern getPattern() {
        return pattern;
    }
    public String getFunctionName() {
        return functionName;
    }
    public void getCommand(){
    }

}
