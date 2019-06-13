package views.graphics;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import views.Graphics;

public class GraphicMatchSelect {
    public VBox deathMatchContainer;
    public VBox captureTheFlagContainer;
    public VBox multiFlagContainer;
    public VBox ExtraContainer;

    public void back(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.mainMenuRoot);
    }

    public void backToMatchSelect(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.matchSelectRoot);
    }

    public void deathMatch(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.deckSelectRoot);
    }

    public void captureTheFlag(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.deckSelectRoot);
    }

    public void multiFlagMode(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.deckSelectRoot);
    }
}
