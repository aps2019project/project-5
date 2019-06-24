package views.menus;

import controllers.logic.Manager;
import views.Command;
import views.Error;
import views.Output;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public interface Menu {

    ArrayList<Command> commands = new ArrayList<>();
    String getMenuName();

    default ArrayList<Command> getCommands() {
        return this.commands;
    }


    default void handleMenu() {
        while(true) {
            String inputCommand = Manager.getInput().getCommand(getMenuName());
            boolean matches = false;
            for(Command command : getCommands()) {
                Matcher matcher = command.getPattern().matcher(inputCommand);
                if(matcher.find()) {
                    if(command.getFunctionName().equals(""))
                        return;
                    matches = true;
                    Method method ;
                    try {
                        method = getClass().getMethod(command.getFunctionName(), Matcher.class);
                        Object object = method.invoke(null, matcher);
                        if(object != null && object.equals(Boolean.FALSE))
                            return;
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            if(!matches)
                Output.err(Error.INVALID_COMMAND.toString());
        }
    }

    static void help(ArrayList<Command> commands) {
        for(Command command : commands) {
            if(!command.hasHelp())
                continue;
            Output.log(String.format("%s\t%s", command.getSampleCommand(), command.getCommandAction()));
        }
    }
}
