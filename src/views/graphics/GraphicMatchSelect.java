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
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void backToMatchSelect(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(MATCH_SELECT_MENU);
    }

    public void deathMatch(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void captureTheFlag(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(MATCH);
    }

    public void multiFlagMode(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(MATCH);
    }


}
