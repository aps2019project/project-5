package views.graphics;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import views.Graphics;

import static views.Graphics.Menu.DECK_SELECTION_MENU;
import static views.Graphics.Menu.MAIN_MENU;

public class GraphicMatchSelect {
    public VBox deathMatchContainer;
    public VBox captureTheFlagContainer;
    public VBox multiFlagContainer;
    public VBox ExtraContainer;

    public void back(MouseEvent mouseEvent) {
        Graphics.setMenu(MAIN_MENU);
    }

    public void backToMatchSelect(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.matchSelectRoot);
    }

    public void deathMatch(MouseEvent mouseEvent) {
        Graphics.setMenu(DECK_SELECTION_MENU);
    }

    public void captureTheFlag(MouseEvent mouseEvent) {
        Graphics.setMenu(DECK_SELECTION_MENU);
    }

    public void multiFlagMode(MouseEvent mouseEvent) {
        Graphics.setMenu(DECK_SELECTION_MENU);
    }
}
