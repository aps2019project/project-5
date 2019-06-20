package views.graphics;

import controllers.ClientManager;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import views.Graphics;
import views.menus.MainMenu;

import java.net.URL;
import java.util.ResourceBundle;

import static views.Graphics.Menu.*;
import static views.Graphics.playMusic;

public class GraphicMainMenu implements Initializable {

    private MediaPlayer music;
    public ImageView foreground;
    public ImageView pillars;
    public AnchorPane root;
    public Label drakes;

    private TranslateTransition foregroundTransition;
    private TranslateTransition pillarsTransition;

    public void shop(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(SHOP_MENU);
    }

    public void matchSelect(MouseEvent mouseEvent) {
        if (!ClientManager.isMainDeckSelected()) {
            Graphics.alert("Sorry", "Can't start game", "You should first select a valid deck as your main deck.");
            return;
        }
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(MULTI_SINGLE);
    }

    public void profile(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.stage.getScene().setRoot(Graphics.profileRoot);
    }

    public void watch(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.stage.getScene().setRoot(Graphics.watchRoot);
    }

    public void collection(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(COLLECTION_MENU);
    }

    public void codex(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(COLLECTION_MENU);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        music = playMusic("music_collection.m4a");

        foregroundTransition = new TranslateTransition(Duration.millis(2000), foreground);
        pillarsTransition = new TranslateTransition(Duration.millis(2000), pillars);
        drakes.setText(ClientManager.getAccount().getDrakeString());

        root.setOnMouseMoved(event -> {
            foregroundTransition.setToX(-event.getX() / 40);
            foregroundTransition.setToY(-event.getY() / 40);
            foregroundTransition.play();

            pillarsTransition.setToX(event.getX() / 100);
            pillarsTransition.setToY(event.getY() / 100);
            pillarsTransition.play();
        });
    }

    public void logout(MouseEvent mouseEvent) {
        music.stop();
        Graphics.setMenu(ACCOUNT_MENU);
    }
}
