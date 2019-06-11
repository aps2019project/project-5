package views.graphics;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import views.Graphics;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void shop(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.shopMenuRoot);
    }

    public void matchSelect(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.matchSelectRoot);
    }

    public void profile(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.profileRoot);
    }
}
