package views.graphics;

import javafx.scene.input.MouseEvent;
import views.Graphics;

import static views.Graphics.Menu.*;

public class MultiSingle {

    public void multiPlayer(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void singlePlayer(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void back(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(MATCH_SELECT_MENU);
    }
}
