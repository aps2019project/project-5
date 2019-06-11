package views.graphics;

import javafx.scene.input.MouseEvent;
import views.Graphics;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphicMainMenu {

    public void shop(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.shopMenuRoot);
    }

    public void matchSelect(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.matchSelectRoot);
    }

    public void profile(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.profileRoot);
    }

    public void watch(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.watchRoot);
    }

    public void collection(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.collectionMenuRoot);
    }

    public void codex(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.collectionMenuRoot);
    }
}
