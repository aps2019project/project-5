package views.menus;

import models.cards.Card;
import views.Command;
import java.util.regex.Matcher;

public class MainMenu extends Menu {

    static {
        commands.add(new Command("^(?i)exit$", ""));
        commands.add(new Command("^(?i)enter\\s+(?i)collection$", "EnterCollection"));
    }

    public static void EnterCollection(Matcher matcher) {

    }

}
