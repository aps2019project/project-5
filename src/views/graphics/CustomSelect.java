package views.graphics;

import javafx.scene.input.MouseEvent;
import views.Graphics;

import static views.Graphics.Menu.MAIN_MENU;

public class CustomSelect {
    public void customGame(MouseEvent mouseEvent) {

    }

    public void storyMode(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
        Graphics.setMenu(MAIN_MENU);
    }
}
