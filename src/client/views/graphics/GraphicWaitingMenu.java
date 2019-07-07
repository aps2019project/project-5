package client.views.graphics;

import client.views.Graphics;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static client.views.Graphics.Menu.CUSTOM_SELECT;

public class GraphicWaitingMenu implements Initializable {

    public Label waitingLabel;
    public static int count = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(() -> {
            count++;
            StringBuilder dots = new StringBuilder();
            for(int i = 0; i < count; i++)
                dots.append(".");
            Platform.runLater(() -> waitingLabel.setText("Waiting for opponent" + dots));
            count %= 3;
        }, 0, 1, TimeUnit.SECONDS);

    }

    public void backToCustomSelect(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_SELECT);
    }
}
