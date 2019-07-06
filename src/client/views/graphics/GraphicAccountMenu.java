package client.views.graphics;

import client.controllers.AccountClient;
import com.jfoenix.controls.*;
import client.controllers.ClientManager;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import client.models.Account;
import client.views.Graphics;
import models.Response;

import java.net.URL;
import java.util.ResourceBundle;

import static client.views.Graphics.Menu.MAIN_MENU;
import static client.views.Graphics.alert;
import static client.views.Graphics.playMusic;

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
    public JFXTabPane tabs;
    public Tab loginTab;

    private MediaPlayer music;

    private TranslateTransition foregroundTransition;
    private TranslateTransition pillarsTransition;


    public void login(MouseEvent mouseEvent) {
        playMusic("sfx_ui_select.m4a");
        AccountClient accountClient = new AccountClient();
        Response response = accountClient.login(loginUsernameTxt.getText(), loginPasswordTxt.getText());
        if (response.OK) {
            Graphics.setMenu(MAIN_MENU);
            music.stop();
            GraphicMainMenu.music = playMusic("music_collection.m4a");
        } else {
            alert("error", "can't login.", response.message);
        }
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
        playMusic("sfx_ui_select.m4a");
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
            signUpUsernameTxt.setText("");
            signUpPasswordTxt.setText("");
            signUpPasswordRematchTxt.setText("");
        } catch (Account.UsernameExistsException ignored) {
            changeAsWrong(signUpUsernameTxt, true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        music = playMusic("music_tutorial.m4a");

        foregroundTransition = new TranslateTransition(Duration.seconds(2), foreground);
        pillarsTransition = new TranslateTransition(Duration.seconds(2), pillars);

        signUpUsernameTxt.textProperty().addListener((observable, oldUsername, newUsername) -> {
            if (ClientManager.accountExists(newUsername))
                changeAsWrong(signUpUsernameTxt, true);
            else
                changeAsWrong(signUpUsernameTxt, false);
        });

        root.setOnMouseMoved(event -> {
            foregroundTransition.setToX(-event.getX() / 40);
            foregroundTransition.setToY(-event.getY() / 40);
            foregroundTransition.play();

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

        loginPasswordTxt.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));
        loginUsernameTxt.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));
        signUpPasswordTxt.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));
        signUpUsernameTxt.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));
        signUpPasswordRematchTxt.setOnMouseClicked(event -> playMusic("sfx_ui_select.m4a"));

        // for just pressing enter for login:
        //        root.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER)
//                if (loginTab.isSelected())
//                    loginBtn.fire();
//                else
//                    signUpBtn.fire();
//        });

        loginBtn.setDefaultButton(true);


    }

}
