package views.graphics;

import javafx.scene.input.MouseEvent;
import views.Graphics;

import static views.Graphics.Menu.*;

public class MultiSingle {

    public void multiPlayer(MouseEvent mouseEvent) {
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void singlePlayer(MouseEvent mouseEvent) {
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void back(MouseEvent mouseEvent) {
        Graphics.setMenu(MATCH_SELECT_MENU);
    }
}
