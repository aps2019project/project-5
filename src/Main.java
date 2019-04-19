import controllers.CommandHandler;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        do{
            CommandHandler.handle();
        } while (CommandHandler.hasNext());
    }
}
