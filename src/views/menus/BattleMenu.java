package views.menus;

import views.Command;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class BattleMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public BattleMenu() {
        // TODO: Add commands.
        commands.add(new Command(
                "^(?i)return$",
                ""
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));

    }

    @Override
    public String getMenuName() {
        return "BattleMenu";
    }

    @Override
    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void gameInfo(Matcher matcher) {}
    public static void showMyMinions(Matcher matcher) {}
    public static void showOpponentMinions(Matcher matcher) {}
    public static void showCardInfo(int cardId) {}
    public static void selectCard(Matcher matcher) {}
    public static void moveTo(Matcher matcher) {}
    public static void attack(Matcher matcher) {}
    public static void useSpecialPower(Matcher matcher) {}
    public static void showHand(Matcher matcher) {}
    public static void insertCard(Matcher matcher) {}
    public static void endTurn(Matcher matcher) {}
    public static void showCollectables(Matcher matcher) {}
    public static void selectCollectable(Matcher matcher) {}
    public static void showCollectableInfo(Matcher matcher) {}
    public static void useCollectable(Matcher matcher) {}

    public static void help(Matcher matcher) {
        Menu.help(new BattleMenu().getCommands());
    }

}
