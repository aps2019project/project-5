package client.layouts;


import client.controllers.WatchClient;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Scoreboard implements Initializable {
    public VBox root;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.getChildren().clear();
        if (WatchClient.getOnlineUsers() != null) WatchClient.getOnlineUsers().forEach(account -> {
            Label label = new Label(account.username);
            root.getChildren().add(label);
        });
    }
}
