package views.graphics;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import views.Graphics;

public class MatchSelect {
    public VBox deathMatchContainer;
    public VBox captureTheFlagContainer;
    public VBox multiFlagContainer;
    public VBox ExtraContainer;

    public void deathMatch(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.deathMatchRoot);
    }

    public void singleFlagMode(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.singleFlagRoot);
    }

    public void multiFlagMode(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.multiFlagRoot);
    }

    public void Extra(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.mainMenuRoot);
    }
}
