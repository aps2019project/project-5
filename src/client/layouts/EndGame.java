package client.layouts;

import client.views.Graphics;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EndGame implements Initializable {
    @FXML
    Label win;
    @FXML
    Label lose;
    public static boolean isWin = false;


    public void back(MouseEvent mouseEvent) {
        Graphics.setMenu(Graphics.Menu.MAIN_MENU);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        win.setVisible(isWin);
        lose.setVisible(!isWin);
    }
}
