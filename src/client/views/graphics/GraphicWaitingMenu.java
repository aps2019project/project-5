package client.views.graphics;

import client.controllers.BattleClient;
import client.views.Graphics;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Response;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static client.views.Graphics.Menu.BATTLE;
import static client.views.Graphics.Menu.CUSTOM_SELECT;

public class GraphicWaitingMenu implements Initializable {

    public Label waitingLabel;
    public static int count = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScheduledThreadPoolExecutor waitingAnimation = new ScheduledThreadPoolExecutor(1);
        waitingAnimation.scheduleAtFixedRate(() -> {
            count++;
            StringBuilder dots = new StringBuilder();
            for(int i = 0; i < count; i++)
                dots.append(".");
            Platform.runLater(() -> waitingLabel.setText("Waiting for opponent" + dots));
            count %= 3;
        }, 0, 1, TimeUnit.SECONDS);


        ScheduledThreadPoolExecutor opponentCheck = new ScheduledThreadPoolExecutor(1);
        opponentCheck.scheduleAtFixedRate(() -> {
            Response response = BattleClient.opponentCheck();
            if(!response.OK) {
                Platform.runLater(() -> Graphics.alert("Error", "Error", response.message));
            } else {
                if(response.data != null) {
                    Platform.runLater(() -> Graphics.setMenu(BATTLE));
                    try {
                        waitingAnimation.awaitTermination(10, TimeUnit.DAYS);
                        opponentCheck.awaitTermination(10, TimeUnit.DAYS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void backToCustomSelect(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_SELECT);
    }
}
