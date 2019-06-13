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
}
