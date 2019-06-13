package views.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.ClientManager;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.Account;
import views.Graphics;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphicAccountMenu implements Initializable {
    public JFXPasswordField signUpPasswordTxt;
    public JFXTextField loginUsernameTxt;
    public JFXPasswordField loginPasswordTxt;
    public JFXButton loginBtn;
    public JFXButton signUpBtn;
    public JFXTextField signUpUsernameTxt;
    public JFXPasswordField signUpPasswordRematchTxt;
    public ImageView pillars;
    public ImageView foreground;
    public AnchorPane root;


    public void login(MouseEvent mouseEvent) {
        boolean isWrong = false;

        try {
            ClientManager.login(loginUsernameTxt.getText(), loginPasswordTxt.getText());
        } catch (Account.InvalidPasswordException e) {
            isWrong = true;
            changeAsWrong(loginPasswordTxt, true);
        } catch (Account.InvalidUsernameException e) {
            isWrong = true;
            changeAsWrong(loginUsernameTxt, true);
        }

        if (isWrong)
            return;

        Graphics.stage.getScene().setRoot(Graphics.mainMenuRoot);
    }

    private void changeAsWrong(JFXTextField textField, boolean isWrong) {
        signUpBtn.setDisable(isWrong);
        if (isWrong) {
            textField.setStyle("-jfx-focus-color: #FF0000; -jfx-unfocus-color: #FF0000;");
        } else {
            textField.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        }
    }

    private void changeAsWrong(JFXPasswordField textField, boolean isWrong) {
        signUpBtn.setDisable(isWrong);
        if (isWrong) {
            textField.setStyle("-jfx-focus-color: #FF0000; -jfx-unfocus-color: #FF0000;");
        } else {
            textField.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        }
    }

    public void signUp(MouseEvent mouseEvent) {
        String username = signUpUsernameTxt.getText();
        String password = signUpPasswordTxt.getText();
        String password2 = signUpPasswordRematchTxt.getText();
        if (password.equals("")) {
            changeAsWrong(signUpPasswordTxt, true);
            return;
        }
        if (!password2.equals(password)) {
            changeAsWrong(signUpPasswordRematchTxt, true);
            return;
        }
        try {
            ClientManager.createAccount(username, password);
            Graphics.alert("Account Created", "Congrats", "Your account created successfully");
        } catch (Account.UsernameExistsException ignored) {
            changeAsWrong(signUpUsernameTxt, true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUpUsernameTxt.textProperty().addListener((observable, oldUsername, newUsername) -> {
            if (ClientManager.accountExists(newUsername))
                changeAsWrong(signUpUsernameTxt, true);
            else
                changeAsWrong(signUpUsernameTxt, false);
        });

        root.setOnMouseMoved(event -> {
            TranslateTransition foregroundTransition = new TranslateTransition(Duration.seconds(4), foreground);
            foregroundTransition.setToX(-event.getX() / 40);
            foregroundTransition.setToY(-event.getY() / 40);
            foregroundTransition.play();

            TranslateTransition pillarsTransition = new TranslateTransition(Duration.seconds(4), pillars);
            pillarsTransition.setToX(event.getX() / 100);
            pillarsTransition.setToY(event.getY() / 100);
            pillarsTransition.play();
        });


        signUpPasswordTxt.textProperty().addListener(((observable, oldPass, newPass) -> {
            if (!newPass.equals(""))
                changeAsWrong(signUpUsernameTxt, false);
        }));


        signUpPasswordRematchTxt.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        signUpUsernameTxt.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        loginUsernameTxt.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        loginPasswordTxt.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        signUpPasswordTxt.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");


        signUpPasswordRematchTxt.textProperty().addListener(((observable, oldPass, newPass) -> {
            if (!newPass.equals(signUpPasswordTxt.getText()))
                changeAsWrong(signUpPasswordRematchTxt, true);
            else
                changeAsWrong(signUpPasswordRematchTxt, false);
        }));

        loginUsernameTxt.textProperty().addListener(((observable, oldValue, newValue) -> {
            changeAsWrong(loginUsernameTxt, false);
        }));


        loginPasswordTxt.textProperty().addListener(((observable, oldValue, newValue) -> {
            changeAsWrong(loginPasswordTxt, false);
        }));
    }
}
