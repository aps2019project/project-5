package views.graphics;

import javafx.fxml.Initializable;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphicBattleController implements Initializable {
    public AnchorPane gameBoard;
    public AnchorPane[][] cell = new AnchorPane[5][9];

    private void createMapCells() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 9; j++) {
                cell[i][j] = new AnchorPane();
                AnchorPane.setLeftAnchor(cell[i][j], 102d * j);
                AnchorPane.setTopAnchor(cell[i][j], 92d * i);
                cell[i][j].setPrefWidth(100);
                cell[i][j].setPrefHeight(90);
                cell[i][j].getStyleClass().add("empty-cell");
                gameBoard.getChildren().add(cell[i][j]);
            }
        }

        PerspectiveTransform e = new PerspectiveTransform();
        e.setUlx(20);    // Upper left
        e.setUly(5);
        e.setUrx(890);    // Upper right
        e.setUry(5);
        e.setLlx(-40);      // Lower left
        e.setLly(480);
        e.setLrx(950);    // Lower right
        e.setLry(480);
        gameBoard.setEffect(e);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createMapCells();
    }
}
