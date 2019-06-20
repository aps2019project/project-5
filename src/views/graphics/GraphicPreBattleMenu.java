package views.graphics;

import controllers.ClientManager;
import javafx.scene.input.MouseEvent;
import views.Graphics;

import static controllers.ClientManager.GameMode.SINGLE_FLAG;
import static controllers.ClientManager.GameMode.STORY_MODE;
import static views.Graphics.Menu.*;

public class CustomSelect {
    public void customGame(MouseEvent mouseEvent) {
        Graphics.setMenu(MATCH_SELECT_MENU);
    }

    public void storyMode(MouseEvent mouseEvent) {
        ClientManager.setGameMode(STORY_MODE);
        Graphics.setMenu(BATTLE);
    }

    public void back(MouseEvent mouseEvent) {
        Graphics.setMenu(MULTI_SINGLE);
    }
}
