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

    private void gameInfo() {}
    private void showMyMinions() {}
    private void showOpponentMinions() {}
    private void showCardInfo(int cardId) {}
    private void selectCard(int cardId) {}
    private void moveTo(int x, int y) {}
    private void attack(int opponentCardId) {}
    private void useSpecialPower() {}
    private void showHand() {}
    private void insertCard(int x, int y) {}
    private void endTurn() {}
    private void showCollectables() {}
    private void selectCollectable(int collectableId) {}
    private void showCollectableInfo() {}
    private void useCollectable(int x, int y) {}
}
