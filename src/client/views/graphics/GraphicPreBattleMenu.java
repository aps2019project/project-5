package client.views.graphics;

import client.controllers.AccountClient;
import client.controllers.BattleClient;
import client.controllers.ChatClient;
import client.controllers.ShellCommand;
import client.views.Graphics;
import com.jfoenix.controls.JFXTextField;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import models.Response;
import models.chat.Chat;
import models.chat.Message;
import models.match.Match;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static client.views.Graphics.Menu.*;
import static client.views.graphics.GraphicBattleController.watchThread;
import static client.views.graphics.GraphicWatchMenu.startWatchServer;

public class GraphicPreBattleMenu implements Initializable {

    public static int matchMode = 1;
    public static boolean isStoryMode = false;
    public static boolean isMultiPlayer = false;

    public VBox chats;
    public VBox deathMatchContainer;
    public VBox captureTheFlagContainer;
    public VBox multiFlagContainer;

    public JFXTextField messageField;

    private ScheduledThreadPoolExecutor waitingAnimation = new ScheduledThreadPoolExecutor(1);

    public void backToCustomSelect(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_SELECT);
    }

    public static void battleRequest() {
        if (isMultiPlayer) {
            Response response = BattleClient.battleRequest(matchMode);
            if (response.data != null) {
                Graphics.setMenu(BATTLE);
                GraphicBattleController.token = Integer.parseInt(((Match) response.data).token);
                watchThread = new Thread(() -> startWatchServer(GraphicBattleController.token));
                watchThread.start();
            } else {
                Graphics.setMenu(WAITING_MENU);
            }
        } else if (!isStoryMode) {
            // TODO: start single player game (with ai)
        }
        ShellCommand.executeCommand("byzanz-record -d 10 lastMatch.mp4");
    }

    public void deathMatch(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
//        try {
//            waitingAnimation.awaitTermination(10, TimeUnit.DAYS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        matchMode = 1;
        battleRequest();
    }

    public void captureTheFlag(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        try {
            waitingAnimation.awaitTermination(10, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        matchMode = 2;
        battleRequest();
    }

    public void multiFlagMode(MouseEvent mouseEvent) {
        Graphics.playMusic("sfx_ui_select.m4a");
        try {
            waitingAnimation.awaitTermination(10, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        messageField.setText("");
        ChatClient.sendMessage(message);
        Platform.runLater(this::updateMessage);
    }

    private void updateMessage() {
        chats.getChildren().clear();
        Chat update = ChatClient.update();
        if (update != null) {
            for (int i = update.messages.size() - 1; i >= 0; i--) {
                chats.getChildren().add(getMessageView(update.messages.get(i)));
            }
        }
    }

    public HBox getMessageView(Message message) {
        HBox messageView = new HBox();
        Label label = new Label(message.user + ":\n" + message.text);
        if (message.user.equals(AccountClient.user.username)) {
            messageView.setAlignment(Pos.CENTER_RIGHT);
            label.getStyleClass().addAll("chat-message", "chat-right");
            label.setTextAlignment(TextAlignment.RIGHT);
        } else {
            messageView.setAlignment(Pos.CENTER_LEFT);
            label.getStyleClass().addAll("chat-message", "chat-left");
            label.setTextAlignment(TextAlignment.LEFT);
        }
        messageView.getStyleClass().add("chat-container");
        messageView.getChildren().addAll(label);
        return messageView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (chats != null)
            waitingAnimation.scheduleAtFixedRate(() -> Platform.runLater(this::updateMessage), 0, 1, TimeUnit.SECONDS);
    }
}
