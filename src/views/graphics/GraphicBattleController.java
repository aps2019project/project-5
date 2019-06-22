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
import models.cards.Attacker;
import models.cards.Card;
import models.map.Cell;
import models.map.Map;

import java.net.URL;
import java.util.HashMap;
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
    public AnchorPane handItem0_container, handItem1_container, handItem2_container, handItem3_container, handItem4_container;
    public ImageView player1ProfileImage, player2ProfileImage;
    public ImageView player1Mana0, player1Mana1, player1Mana2, player1Mana3, player1Mana4, player1Mana5, player1Mana6, player1Mana7, player1Mana8;
    public ImageView player2Mana0, player2Mana1, player2Mana2, player2Mana3, player2Mana4, player2Mana5, player2Mana6, player2Mana7, player2Mana8;
    public Label player1Name, player2Name;
    private boolean isGraveyardOpen = false;
    private ImageView[] handItemImages = new ImageView[5];
    private ImageView[] player1Mana = new ImageView[9];
    private ImageView[] player2Mana = new ImageView[9];
    private Label[] handItemMana = new Label[5];
    private AnchorPane[] handItemContainer = new AnchorPane[5];
    private Hand hand;
    private HashMap<Card, AnchorPane> cardViews = new HashMap<>();

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
        copyHandViewsToArray();
        updateHand();
        showProfiles();
        updateMana();
        player1Name.setText(ClientManager.getPlayingMatch().getPlayer1().getAccount().getUsername().toUpperCase());
        player2Name.setText(ClientManager.getPlayingMatch().getPlayer2().getAccount().getUsername().toUpperCase());
        showCardsInBoard();

//        for(int i = 0; i < 5; i++)
//        for(int j = 0; j < 9; j++) {
//            AnchorPane cardPane = getCardInGame(i, j);
//            root.getChildren().add(cardPane);
//        }
    }

    private void showCardsInBoard() {
        root.getChildren().removeAll(cardViews.values());
        cardViews.clear();
        Map map = ClientManager.getPlayingMatch().getMap();
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 9; j++) {
                try {
                    Cell cell = map.getCell(i, j);
                    Attacker attacker = cell.getAttacker();
                    if(attacker == null)
                        continue;
                    AnchorPane cardAnchorPane = getCardInGame(attacker, i, j);
                    cardViews.put(attacker, cardAnchorPane);
                    root.getChildren().add(cardAnchorPane);
                } catch (Map.InvalidCellException ignored) {}
            }
    }

    private void showProfiles() {
        String player1HeroName = ClientManager.getPlayingMatch().getPlayer1().getDeck().getHero().getName();
        String player2HeroName = ClientManager.getPlayingMatch().getPlayer2().getDeck().getHero().getName();
        player1ProfileImage.setImage(new Image(
                "/resources/images/cards/" + player1HeroName + "_logo.png"
        ));
        player2ProfileImage.setImage(new Image(
                "/resources/images/cards/" + player2HeroName + "_logo.png"
        ));
    }

    private void copyHandViewsToArray() {
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
        handItemContainer[0] = handItem0_container;
        handItemContainer[1] = handItem1_container;
        handItemContainer[2] = handItem2_container;
        handItemContainer[3] = handItem3_container;
        handItemContainer[4] = handItem4_container;
        player1Mana[0] = player1Mana0;
        player1Mana[1] = player1Mana1;
        player1Mana[2] = player1Mana2;
        player1Mana[3] = player1Mana3;
        player1Mana[4] = player1Mana4;
        player1Mana[5] = player1Mana5;
        player1Mana[6] = player1Mana6;
        player1Mana[7] = player1Mana7;
        player1Mana[8] = player1Mana8;
        player2Mana[0] = player2Mana0;
        player2Mana[1] = player2Mana1;
        player2Mana[2] = player2Mana2;
        player2Mana[3] = player2Mana3;
        player2Mana[4] = player2Mana4;
        player2Mana[5] = player2Mana5;
        player2Mana[6] = player2Mana6;
        player2Mana[7] = player2Mana7;
        player2Mana[8] = player2Mana8;
    }

    public void updateHand() {
        hand = ClientManager.getHand();
        System.out.println("Hand: \n\t" + hand.getCards().toString());
        int index = 0;
        for(Card card : hand.getCards()) {
            handItemMana[index].setText("" + card.getManaPoint());
            handItemImages[index].setImage(new Image("/resources/images/cards/" + card.getName() + "_idle.gif"));
            int finalIndex = index;
            handItemContainer[index].setOnMouseEntered(event -> {
                AnchorPane cardPane = ShopController.getCardPane(card, false);
                cardPane.relocate(handItemContainer[finalIndex].localToScene(
                        handItemContainer[finalIndex].getBoundsInLocal()
                ).getMinX(), 600);
                root.getChildren().add(cardPane);
                handItemContainer[finalIndex].setOnMouseExited(event1 -> root.getChildren().remove(cardPane));
            });
            index++;
        }
    }

    public void updateMana() {
        Image mana = new Image("/resources/images/battle/ui/icon_mana@2x.png");
        Image noMana = new Image("/resources/images/battle/ui/icon_mana_inactive@2x.png");
        int mana1 = ClientManager.getPlayingMatch().getPlayer1().getMana();
        int mana2 = ClientManager.getPlayingMatch().getPlayer1().getMana();
        for(int i = 0; i < 9; i++)
            player1Mana[i].setImage(i < mana1 ? mana : noMana);
        for(int i = 0; i < 9; i++)
            player2Mana[i].setImage(i < mana2 ? mana : noMana);

    }

    public void graveyardToggle(MouseEvent mouseEvent) {
        if(!isGraveyardOpen) {
            graveyardButton.getStyleClass().remove("button-open");
            graveyardButton.getStyleClass().add("button-close");
            TranslateTransition t = new TranslateTransition(new Duration(500), graveyardContainer);
            t.setToX(115);
            t.play();
        } else {
            graveyardButton.getStyleClass().add("button-open");
            graveyardButton.getStyleClass().remove("button-close");
            TranslateTransition t = new TranslateTransition(new Duration(500), graveyardContainer);
            t.setToX(0);
            t.play();
        }
        isGraveyardOpen = !isGraveyardOpen;
    }

    private AnchorPane getCardInGame(Card card, int row, int column) {
        AnchorPane anchorPane = new AnchorPane();
        ImageView imageView = new ImageView();
        int scale = 1;
        if(column > 4) scale = -1;
        imageView.setScaleX(scale);
        imageView.setFitWidth((160 + row * 8));
        imageView.setFitHeight(160 + row * 8);
        imageView.setImage(new Image("/resources/images/cards/" + card.getName() + "_idle.gif"));
        anchorPane.relocate(483 + column * 97 + (column - 4) * row * 2.5, 250 + 90 * row);

        anchorPane.getChildren().add(imageView);
        return anchorPane;
    }
}
