package client.views.graphics;

import client.controllers.ClientManager;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import client.views.Graphics;

import javax.security.auth.login.AccountNotFoundException;

import static client.controllers.ClientManager.GameMode.*;
import static client.controllers.ClientManager.GameMode.MULTI_FLAG;
import static client.views.Graphics.Menu.*;

public class GraphicPreBattleMenu {

    public VBox deathMatchContainer;
    public VBox captureTheFlagContainer;
    public VBox multiFlagContainer;


    public void backToCustomSelect(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void deathMatch(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        ClientManager.setGameMode(DEATH_MATCH);
        Graphics.setMenu(BATTLE);
    }

    public void captureTheFlag(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        ClientManager.setGameMode(SINGLE_FLAG);
        Graphics.setMenu(BATTLE);
    }

    public void multiFlagMode(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        ClientManager.setGameMode(MULTI_FLAG);
        Graphics.setMenu(BATTLE);
    }


    public void customGame(MouseEvent mouseEvent) {
        Graphics.setMenu(MATCH_SELECT_MENU);
    }

    public void storyMode(MouseEvent mouseEvent) {
        ClientManager.setGameMode(STORY_MODE);

        Graphics.setMenu(BATTLE);
    }

    public void backToMultiSingle(MouseEvent mouseEvent) {
        Graphics.setMenu(MULTI_SINGLE);
    }

    public void multiPlayer(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        try {
            ClientManager.setOpponent("", false); // TODO: 6/20/19 add other player for phase 3
        } catch (AccountNotFoundException ignored) { }
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void singlePlayer(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        try {
            ClientManager.setOpponent("", true);
        } catch (AccountNotFoundException ignored) { }
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(MAIN_MENU);
    }
}
