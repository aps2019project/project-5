package client.views.graphics;

import client.controllers.AccountClient;
import client.controllers.BattleClient;
import client.controllers.ChatClient;
import client.controllers.ClientManager;
import client.models.Player;
import com.jfoenix.controls.JFXTextField;
import com.sun.security.ntlm.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import client.views.Graphics;
import models.Response;
import models.chat.Chat;
import models.chat.Message;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static client.views.Graphics.Menu.*;

public class GraphicPreBattleMenu implements Initializable {

    public VBox deathMatchContainer;
    public VBox captureTheFlagContainer;
    public VBox multiFlagContainer;

    private static int matchMode = 1;
    private static boolean isStoryMode = false;
    private static boolean isMultiPlayer = false;
    public VBox chats;
    public JFXTextField messageField;

    public void backToCustomSelect(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_SELECT);
    }


    public void battleRequest() {
        if (isMultiPlayer) {
            Response response = BattleClient.battleRequest(matchMode);
            if (response.data != null) {
                Graphics.setMenu(BATTLE);
            } else {
                Graphics.setMenu(WAITING_MENU);
            }
        } else if (!isStoryMode) {
            // TODO: start single player game (with ai)
        }
    }

    public void deathMatch(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        matchMode = 1;
        battleRequest();
    }

    public void captureTheFlag(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        matchMode = 2;
        battleRequest();
    }

    public void multiFlagMode(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        matchMode = 3;
        battleRequest();
    }

    public void customGame(MouseEvent mouseEvent) {
        Graphics.setMenu(MATCH_SELECT_MENU);
    }

    public void storyMode(MouseEvent mouseEvent) {
        isStoryMode = true;
        battleRequest();
    }

    public void backToMultiSingle(MouseEvent mouseEvent) {
        Graphics.setMenu(MULTI_SINGLE);
    }

    public void multiPlayer(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        isMultiPlayer = true;
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void singlePlayer(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        isMultiPlayer = false;
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(MAIN_MENU);
    }

    public void sendMessage(ActionEvent actionEvent) {
        String message = messageField.getText();
        ChatClient.sendMessage(message);
        Platform.runLater(this::updateMessage);
    }

    private void updateMessage() {
        chats.getChildren().clear();
        Chat update = ChatClient.update();
        if (update != null) {
            for (int i = update.messages.size() - 1; i >= 0; i--) {
                chats.getChildren().add(getMessageView(update.messages.get(i)));
                System.out.println(update.messages.get(i).text);
            }
        } else {
            System.out.println("update is null");
        }
    }

    public HBox getMessageView(Message message) {
        HBox messageView = new HBox();
        Label label = new Label(message.text);
        if (message.user.equals(AccountClient.user.username)) {
            messageView.setAlignment(Pos.CENTER_RIGHT);
            label.getStyleClass().addAll("chat-message", "chat-right");
        } else {
            messageView.setAlignment(Pos.CENTER_LEFT);
            label.getStyleClass().addAll("chat-message", "chat-left");
        }
        messageView.getStyleClass().add("chat-container");
        messageView.getChildren().addAll(label);
        return messageView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ScheduledThreadPoolExecutor waitingAnimation = new ScheduledThreadPoolExecutor(1);
        if (chats != null)
            waitingAnimation.scheduleAtFixedRate(() -> Platform.runLater(this::updateMessage), 0, 1, TimeUnit.SECONDS);
    }
}
