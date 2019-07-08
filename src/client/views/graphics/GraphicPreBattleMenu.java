package client.views.graphics;

import client.controllers.AccountClient;
import client.controllers.BattleClient;
import client.controllers.ChatClient;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import client.views.Graphics;
import models.Response;
import models.chat.Message;

import static client.views.Graphics.Menu.*;

public class GraphicPreBattleMenu {

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
        Response response = ChatClient.update();


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
}
