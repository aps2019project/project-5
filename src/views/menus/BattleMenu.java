package views.menus;

import views.Command;

import java.util.ArrayList;

public class BattleMenu implements Menu {
    private ArrayList<Command> commands = new ArrayList<>();

    public BattleMenu() {
        // TODO: Add commands
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void gameInfo() {}
    public static void showMyMinions() {}
    public static void showOpponentMinions() {}
    public static void showCardInfo(int cardId) {}
    public static void selectCard(int cardId) {}
    public static void moveTo(int x, int y) {}
    public static void attack(int opponentCardId) {}
    public static void useSpecialPower() {}
    public static void showHand() {}
    public static void insertCard(int x, int y) {}
    public static void endTurn() {}
    public static void showCollectables() {}
    public static void selectCollectable(int collectableId) {}
    public static void showCollectableInfo() {}
    public static void useCollectable(int x, int y) {}
}
