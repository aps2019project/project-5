package views.menus;

import views.Command;
import views.Error;
import views.Input;
import views.Output;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;

public interface Menu {

    String getMenuName();
    ArrayList<Command> getCommands();

    default void handleMenu() {
        while(true) {
            String inputCommand = Input.getCommand(getMenuName());
            boolean matches = false;
            for(Command command : getCommands()) {
                Matcher matcher = command.getPattern().matcher(inputCommand);
                if(matcher.find()) {
                    if(command.getFunctionName().equals(""))
                        return;
                    matches = true;
                    try {
                        Method method = getClass().getMethod(command.getFunctionName(), Matcher.class);
                        method.invoke(null, matcher);
                    } catch (Exception exception) {
                        Output.err(exception.getMessage());
                    }
                }
            }
            if(!matches)
                Output.err(Error.INVALID_COMMAND.toString());
        }
    }
}
