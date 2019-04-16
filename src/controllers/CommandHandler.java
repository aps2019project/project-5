package controllers;

import models.Game;
import models.match.Match;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler {

    private static String phrase;
    public static Scanner scanner = new Scanner(System.in);
    private static boolean hasNext;
    public static Game game;


    enum Command {
        START("start", "start"),

        CREATE_ACCOUNT("", ""),
        LOGIN("", ""),
        SHOW_LEADERBOARD("", ""),
        SAVE("", ""),
        LOGOUT("", ""),
        HELP("", ""),

        END("end", "end");


        Pattern pattern;

        String functionName;
        Command(String pattern, String functionName){
            this.pattern = Pattern.compile(pattern);
            this.functionName = functionName;
        }

    }

    private static void getPhrase(){
        phrase = scanner.next().trim();
    }

    public static boolean hasNext() {
        return hasNext;
    }


    public static void handle() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        getPhrase();
        for (Command command:Command.values()) {
            Matcher matcher = command.pattern.matcher(phrase);
            if(matcher.matches()){
                Method commandMethod = CommandHandler.class.getMethod(command.functionName);
                commandMethod.invoke(null, null);
                break;
            }
        }
    }

    private void start(){

        return;
    }


    private void end() {
        hasNext = false;
    }

}
