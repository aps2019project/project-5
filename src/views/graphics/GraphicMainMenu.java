package views.graphics;

import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import views.Graphics;
import java.net.URL;
import java.util.ResourceBundle;

public class GraphicMainMenu implements Initializable {

    public ImageView foreground;
    public ImageView pillars;
    public AnchorPane root;

    private TranslateTransition foregroundTransition;
    private TranslateTransition pillarsTransition;

    public void shop(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.shopMenuRoot);
    }

    public void matchSelect(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.matchSelectRoot);
    }

    public void profile(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.profileRoot);
    }

    public void watch(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.watchRoot);
    }

    public void collection(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.collectionMenuRoot);
    }

    public void codex(MouseEvent mouseEvent) {
        Graphics.stage.getScene().setRoot(Graphics.collectionMenuRoot);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foregroundTransition = new TranslateTransition(Duration.millis(2000), foreground);
        pillarsTransition = new TranslateTransition(Duration.millis(2000), pillars);

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
        Graphics.stage.getScene().setRoot(Graphics.accountMenuRoot);
    }
}
