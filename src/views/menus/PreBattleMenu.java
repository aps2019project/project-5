package views.menus;

import controllers.ClientManager;
import controllers.logic.Manager;
import models.Account;
import views.Command;
import views.Error;
import views.Input;
import views.Output;

import java.util.ArrayList;

import static views.Error.WRONG_CHOICE;

public class PreBattleMenu implements Menu {

    @Override
    public String getMenuName() {
        return "BattleMenu";
    }

    @Override
    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    private int askQuestion(String question, String... choices) {
        Output.log(question);
        int i = 1;
        for (String choice : choices) {
            Output.log(i++ + ". " + choice);
        }
        int result = 0;
        while (result == 0) {
            String chosen = Input.getString("");
            try {
                result = Integer.parseInt(chosen);
                if (result < 1 || result > choices.length)
                    result = 0;
            } catch (Exception e) {
                for (int j = 0; j < choices.length; j++)
                    if (chosen.trim().equalsIgnoreCase(choices[j]))
                        return j + 1;
            }
            if (result == 0)
                Output.err(WRONG_CHOICE);
        }
        return result;
    }

    @Override
    public void handleMenu() {

        int result = askQuestion("Select your game state:", "Story Game", "Custom Game");
        int gameMode = result;

        result = askQuestion("Choose Player Numbers:", "Single Player", "Multi Player");
        boolean AIMode = result == 1;

        if (gameMode == 2) {
            result = askQuestion("Select Game Mode:", "Death match", "Multi flag mode", "Single flag mode");
            gameMode = result + 1;
        }
        if (result > 1) {

            if (result == 2)
                result = 1;
            if (result == 3) {
                Output.log("How many flags do you want to have?(between 2 and 9");
                result = 0;
                do {
                    try {
                        result = Integer.parseInt(Input.getString(""));
                    } catch (Exception ignored) {
                    }
                    if (!(result < 2 || result > 9))
                        Output.log("Enter a valid number !");
                } while (result < 2 || result > 9);
            }
            Manager.setMapFlags(result);
        }

        String opponentName = "";
        if (!AIMode) {
            Output.log("Enter opponent's name:");
            int attemptsCount = 0;
            while (opponentName.equals("") && attemptsCount < 3) {
                attemptsCount++;
                opponentName = Input.getString("");
                try {
                    if (!Manager.canPlay(opponentName)) {
                        Output.err(Error.PLAYERS_DECK_IS_NOT_VALID);
                        opponentName = "";
                    }
                } catch (Account.InvalidUsernameException e) {
                    Output.err(Error.USERNAME_NOT_FOUND);
                    opponentName = "";
                } catch (Account.CantPlayWithYourselfException e) {
                    Output.err(Error.CANT_PLAY_WITH_YOURSELF);
                    opponentName = "";
                }
            }
        } else {
            opponentName = "AI User";

        }
        Manager.setMatchData(AIMode, ClientManager.GameMode.getGame(gameMode), opponentName);

    }
}
