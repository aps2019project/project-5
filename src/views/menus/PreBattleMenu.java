package views.menus;

import controllers.Manager;
import models.Account;
import models.Collection;
import views.Command;
import views.Error;
import views.Input;
import views.Output;

import javax.security.auth.login.AccountException;
import javax.swing.plaf.OptionPaneUI;
import java.nio.channels.AcceptPendingException;
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
        int i = 1 ;
        for (String choice:choices) {
            Output.log(i++ + ". " + choice );
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
            if(result == 0)
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

        if(gameMode == 2) {
            result = askQuestion("Select Game Mode:", "Death match", "Multi flag mode", "Single flag mode");
            gameMode = result + 1;
        }

        String opponentName = "";
        if(!AIMode) {
            Output.log("Enter opponent's name:");
            int attemptsCount = 0;
            while (opponentName.equals("") && attemptsCount < 3) {
                attemptsCount++;
                opponentName = Input.getString("");
                try {
                    if(!Manager.canPlay(opponentName)) {
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
        Manager.setMatchData(AIMode, gameMode, opponentName);

    }
}
