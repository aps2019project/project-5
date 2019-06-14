package views.graphics;

import javafx.scene.input.MouseEvent;
import views.Graphics;

import static views.Graphics.Menu.*;

public class CustomSelect {
    public void customGame(MouseEvent mouseEvent) {
        Graphics.setMenu(MATCH_SELECT_MENU);
    }

    public void storyMode(MouseEvent mouseEvent) {
        Graphics.setMenu(MATCH);
    }

    public void back(MouseEvent mouseEvent) {
        Graphics.setMenu(MULTI_SINGLE);
    }
}
