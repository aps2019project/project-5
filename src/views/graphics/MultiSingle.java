package views.graphics;

import javafx.scene.input.MouseEvent;
import views.Graphics;

import static views.Graphics.Menu.MATCH_SELECT_MENU;

public class MultiSingle {

    public void multiPlayer(MouseEvent mouseEvent) {
    }

    public void singlePlayer(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
        Graphics.setMenu(MATCH_SELECT_MENU);
    }
}
