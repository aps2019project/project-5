package views.menus;

import views.Command;
import views.Error;
import views.Input;
import views.Output;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;

public interface Menu {

    ArrayList<Command> commands = new ArrayList<>();
    String getMenuName();

    default ArrayList<Command> getCommands() {
        return this.commands;
    }


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
                        try {
                            if (method.invoke(null, matcher).equals(Boolean.FALSE))
                                return;
                        } catch (Exception ignored) {
                            System.out.println("hamnoon catch exception bikhodeeeeeeeeeeeeeeee!");
                        }
                    } catch (Exception exception) {
                        Output.err(exception.getMessage());
                    }
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
