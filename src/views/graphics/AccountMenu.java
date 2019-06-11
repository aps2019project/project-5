package views.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import views.Graphics;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountMenu implements Initializable {

    @FXML JFXTextField loginUsername;
    @FXML JFXPasswordField loginPassword;
    @FXML JFXButton loginButton;
    @FXML JFXTextField signUpUsername;
    @FXML JFXPasswordField signUpPassword;
    @FXML JFXPasswordField signUpPasswordRematch;
    @FXML JFXButton signUpButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.mainMenuRoot);
    }
}
