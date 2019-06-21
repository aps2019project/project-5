package views.graphics;

import controllers.ClientManager;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import models.Hand;
import models.cards.Card;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphicBattleController implements Initializable {
    public AnchorPane gameBoard;
    public AnchorPane[][] cell = new AnchorPane[5][9];
    public Button graveyardButton;
    public AnchorPane root;
    public AnchorPane graveyardContainer;
    public VBox graveyardCards; // Dead cards must be added to it's children.
    public ImageView handItem0_image, handItem1_image, handItem2_image, handItem3_image, handItem4_image;
    public Label handItem0_label, handItem1_label, handItem2_label, handItem3_label, handItem4_label;
    private boolean isGraveyardOpen = false;
    private ImageView[] handItemImages = new ImageView[5];
    private Label[] handItemMana = new Label[5];
    private Hand hand;

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
        handItemImages[0] = handItem0_image;
        handItemImages[1] = handItem1_image;
        handItemImages[2] = handItem2_image;
        handItemImages[3] = handItem3_image;
        handItemImages[4] = handItem4_image;
        handItemMana[0] = handItem0_label;
        handItemMana[1] = handItem1_label;
        handItemMana[2] = handItem2_label;
        handItemMana[3] = handItem3_label;
        handItemMana[4] = handItem4_label;
        updateHand();
    }

    public void updateHand() {
        hand = ClientManager.getHand();
        System.out.println("Hand: \n\t" + hand.getCards().toString());
        int index = 0;
        for(Card card : hand.getCards()) {
            handItemMana[index].setText("" + card.getManaPoint());
            handItemImages[index].setImage(new Image("/resources/images/cards/" + card.getName() + "_idle.gif"));
            index++;
        }
    }

    public void graveyardToggle(MouseEvent mouseEvent) {
        if(!isGraveyardOpen) {
            graveyardButton.getStyleClass().remove("button-open");
            graveyardButton.getStyleClass().add("button-close");
            TranslateTransition t = new TranslateTransition(new Duration(1000), graveyardContainer);
            t.setToX(115);
            t.play();
        } else {
            graveyardButton.getStyleClass().add("button-open");
            graveyardButton.getStyleClass().remove("button-close");
            TranslateTransition t = new TranslateTransition(new Duration(1000), graveyardContainer);
            t.setToX(0);
            t.play();
        }
        isGraveyardOpen = !isGraveyardOpen;
    }
}
