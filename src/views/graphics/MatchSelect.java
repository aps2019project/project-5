package views.graphics;

import javafx.scene.input.MouseEvent;
import views.Graphics;

public class MatchSelect {
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
