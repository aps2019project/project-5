package views.graphics;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import views.Graphics;

import java.net.URL;
import java.util.ResourceBundle;

import static views.Graphics.Menu.*;

public class GraphicMatchSelect {
    public VBox deathMatchContainer;
    public VBox captureTheFlagContainer;
    public VBox multiFlagContainer;


    public void back(MouseEvent mouseEvent) {
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void backToMatchSelect(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.matchSelectRoot);
    }

    public void deathMatch(MouseEvent mouseEvent) {
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void captureTheFlag(MouseEvent mouseEvent) {
        Graphics.setMenu(MATCH);
    }

    public void multiFlagMode(MouseEvent mouseEvent) {
        Graphics.setMenu(MATCH);
    }


}
