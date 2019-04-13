package views.menus;

import views.Command;
import views.Error;
import views.Input;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class Menu {
    protected static ArrayList<Command> commands = new ArrayList<>();

    public static void setCommands() {}
    public static void showMenu() {}

    public static void handleMenu() {
        while(true) {
            String inputCommand = Input.getCommand();
            boolean matches = false;
            for(Command command : commands) {
                Matcher matcher = command.getPattern().matcher(inputCommand);
                if(matcher.find()) {
                    if(command.getFunctionName().equals(""))
                        return;
                    matches = true;
                    try {
                        Method method = MainMenu.class.getMethod(command.getFunctionName(), Matcher.class);
                        method.invoke(null, matcher);
                    } catch (Exception exception) {
                        System.err.println(exception.getMessage());
                    }
                }
            }
            if(!matches)
                System.err.println(Error.INVALID_COMMAND);
        }
    }
}
