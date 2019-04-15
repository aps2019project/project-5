package views.menus;

import views.Command;
import views.Error;
import views.Input;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;

public interface Menu {

    ArrayList<Command> getCommands();

    default void handleMenu() {
        while(true) {
            String inputCommand = Input.getCommand();
            boolean matches = false;
            for(Command command : getCommands()) {
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
