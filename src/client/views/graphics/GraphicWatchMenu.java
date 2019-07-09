package client.views.graphics;

import client.controllers.WatchClient;
import client.views.Graphics;
import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import models.match.Match;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static client.views.Graphics.Menu.MAIN_MENU;

public class GraphicWatchMenu implements Initializable {

    public static ArrayList<Match> matchesList;
    static boolean isStartStream = false;
    public static ImageView watch;
    public static AnchorPane root;
    public static VBox liveMatchesBox;
    public ImageView backBtn;
    public static Map<String, Integer> matches = new HashMap<>();


    public static void startWatchClient(int port) {
        if (!isStartStream) {
            String ip = "127.0.0.1";
            isStartStream = true;
            new Thread(() -> {
                try {
                    while (isStartStream) {
                        Socket soc = new Socket(ip, port);
                        BufferedImage img = ImageIO.read(soc.getInputStream());

//                        System.out.println("root = " + root);
//                        System.out.println("watch = " + watch);
//                        System.out.println("liveMatchesBox = " + liveMatchesBox);
                        watch.setImage(SwingFXUtils.toFXImage(img, null));
                        soc.close();

                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                isStartStream = false;
            }).start();
        } else {
            isStartStream = false;
        }
    }

    static boolean isStart = false;
    public static void startWatchServer(int port) {
        if(isStartStream){
            Graphics.alert("Error", "Sorry", "If you want to start a server. You must stop watching other's screen.");
            return;
        }
        if (!isStart) {
            isStart = true;
            new Thread(() -> {
                try {
                    Robot rob = new Robot();
                    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                    while (isStart) {
                        try {
                            ServerSocket soc = new ServerSocket(port);
                            Socket so = soc.accept();
                            BufferedImage img = rob.createScreenCapture(new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));

                            ByteArrayOutputStream ous = new ByteArrayOutputStream();
                            ImageIO.write(img, "png", ous);
                            so.getOutputStream().write(ous.toByteArray());
                            soc.close();
                            try {
                                Thread.sleep(10);
                            } catch (Exception e) {
                            }
                        } catch (Exception ignored) { }
                    }
                } catch (Exception e) {e.printStackTrace();
                    JOptionPane.showMessageDialog(null, e);
                }
                isStart = false;
            }).start();
        } else {
            isStart = false;
        }
    }


    public void back(MouseEvent mouseEvent) {
        Graphics.setMenu(MAIN_MENU);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        matchesList = WatchClient.getLiveMatches();
        liveMatchesBox.getChildren().clear();
        matches.clear();
        for (Match match :matchesList) {
            String buttonText = match.players[0].account.username + " - " + match.players[1].account.username;
            JFXButton button = new JFXButton(buttonText);
            button.getStyleClass().addAll("watch-button");
            matches.put(buttonText, Integer.parseInt(match.token));
            button.setOnMouseClicked(event -> startWatchClient(matches.get(button.getText())));
            liveMatchesBox.getChildren().add(button);
        }


    }
}
