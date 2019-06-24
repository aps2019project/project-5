package views.graphics;

import controllers.ClientManager;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import models.Action;
import models.Collection;
import models.Hand;
import models.Player;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.spell.Spell;
import models.map.Cell;
import models.map.Map;
import models.match.Match;
import views.Command;
import views.Graphics;
import views.SpriteMaker;
import views.menus.BattleMenu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

import static views.Graphics.playMusic;

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
    public HBox mana1BarContainer, mana2BarContainer;
    private boolean isGraveyardOpen = false;
    private ImageView[] handItemImages = new ImageView[5];
    private ImageView[] player1Mana = new ImageView[9];
    private ImageView[] player2Mana = new ImageView[9];
    private Label[] handItemMana = new Label[5];
    private AnchorPane[] handItemContainer = new AnchorPane[5];
    private HashMap<Card, AnchorPane> cardViews = new HashMap<>();
    private Card selectedCard;
    private boolean isSelectedCardInGame = false;

    private void createMapCells() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                cell[i][j] = new AnchorPane();
                AnchorPane.setLeftAnchor(cell[i][j], 102d * j);
                AnchorPane.setTopAnchor(cell[i][j], 92d * i);
                int finalI = i, finalJ = j;
                cell[i][j].setOnMouseClicked(event -> clickCell(finalI, finalJ));
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
        player1Name.setRotate(-3.0);
        player2Name.setRotate(3.0);
        mana1BarContainer.setRotate(-3.0);
        mana2BarContainer.setRotate(3.0);
        showCardsInBoard();
        handItem0_container.setLayoutX(-100);
    }

    private void showCardsInBoard() {
        root.getChildren().removeAll(cardViews.values());
        cardViews.clear();
        Map map = ClientManager.getPlayingMatch().getMap();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                try {
                    Cell cell = map.getCell(i, j);
                    Attacker attacker = cell.getAttacker();
                    if (attacker == null)
                        continue;
                    AnchorPane cardAnchorPane = getCardInGame(attacker, i, j);
                    cardViews.put(attacker, cardAnchorPane);
                    setCard(cardAnchorPane);
                } catch (Map.InvalidCellException ignored) {
                }
            }
    }

    private Rectangle getCardRectangle(int row, int column) {
        double x = 483 + column * 97 + (column - 4) * row * 2.5;
        double y = 250 + 90 * row;
        double width = 160 + row * 8;
        double height = 160 + row * 8;
        return new Rectangle(x, y, width, height);
    }

    private void setCard(AnchorPane cardAnchorPane) {
        root.getChildren().add(cardAnchorPane);
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

    private int getDistance(double x1, double y1, double x2, double y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void moveCard(AnchorPane cardPane, Rectangle newPosition, Card card) {
        int time = getDistance(newPosition.getX(), newPosition.getY(), cardPane.getLayoutX(), cardPane.getLayoutY()) * 7;
        TranslateTransition t = new TranslateTransition(new Duration(time), cardPane);
        t.setToX(newPosition.getX() - cardPane.getLayoutX());
        t.setToY(newPosition.getY() - cardPane.getLayoutY());
        t.play();
        new Thread(() -> {
            ((ImageView) cardPane.getChildren().get(0)).setImage(new Image("/resources/images/cards/" + card.getName() + "_run.gif"));
            long newTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - newTime <= time) {
            }
            ((ImageView) cardPane.getChildren().get(0)).setImage(new Image("/resources/images/cards/" + card.getName() + "_idle.gif"));

        }).start();
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

    private Card getCardInCell(int x, int y) {
        for (java.util.Map.Entry<Card, AnchorPane> cardView : cardViews.entrySet())
            if (cardView.getKey().getCell().getX() == x && cardView.getKey().getCell().getY() == y)
                return cardView.getKey();
        return null;
    }

    private void clickCell(int row, int column) {
        if (ClientManager.isAITurn())
            return;
        Card clickedCard = getCardInCell(row, column);
        if (selectedCard == null) {
            if (clickedCard != null) try {
                ClientManager.selectCard(clickedCard.getID());
                selectedCard = clickedCard;
                isSelectedCardInGame = true;
                System.out.println("Card Selected");
            } catch (Collection.CardNotFoundException e) {
                System.out.println("Can't Select Card");
            }
        } else {
            if (selectedCard.equals(clickedCard)) {
                selectedCard = null;
                System.out.println("Card unselected");
            } else {
                if (isSelectedCardInGame) {
                    // TODO: move
                    try {
                        ClientManager.moveTo(row + 1, column + 1);
                        moveCard(cardViews.get(selectedCard), getCardRectangle(row, column), selectedCard);
                    } catch (Match.InvalidMoveException | Map.InvalidCellException e) {
                        System.out.println("can't move here");
                    }
                } else {
                    // TODO: insert
                    try {
                        ClientManager.insertCard(selectedCard.getID(), row + 1, column + 1);
                        AnchorPane cardPane = getCardInGame(selectedCard, row, column);
                        AnchorPane teleport = new AnchorPane(SpriteMaker.getAndShowAnimation(new ImageView(), "teleport", Action.TELEPORT, 1),
                                SpriteMaker.getAndShowAnimation(new ImageView(), "teleport1", Action.TELEPORT, 1),
                                SpriteMaker.getAndShowAnimation(new ImageView(), "teleport2", Action.TELEPORT, 1),
                                SpriteMaker.getAndShowAnimation(new ImageView(), "teleport3", Action.TELEPORT, 1),
                                SpriteMaker.getAndShowAnimation(new ImageView(), "teleport4", Action.TELEPORT, 1));
                        Rectangle rect = getCardRectangle(row - 1, column - 1);
                        teleport.setLayoutX(rect.getX() + 150);
                        teleport.setLayoutY(rect.getY() + 140);
                        teleport.setScaleX(2);
                        teleport.setScaleY(2);
//                        removeCard(teleport);
                        setCard(cardPane);
                        setCard(teleport);
                        selectedCard = null;
                        updateMana();
                    } catch (Player.NotEnoughManaException | Map.InvalidCellException
                            | Map.InvalidTargetCellException | Player.HeroDeadException
                            | Collection.CardNotFoundException ignored) {
                    }
                }
            }
        }
        updateCells();
    }

    private void removeCard(AnchorPane cardPane) {
        root.getChildren().remove(cardPane);
    }

    private void updateCells() {
        String[] removingStyleClassList = {"selected-card-cell", "can-insert-cell", "can-move-cell"};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                cell[i][j].getStyleClass().removeAll(removingStyleClassList);
                cell[i][j].getStyleClass().add("empty-cell");
            }
        }
        if (selectedCard != null) {
            if (isSelectedCardInGame) {
                cell[selectedCard.getCell().getX()][selectedCard.getCell().getY()].getStyleClass().add("selected-card-cell");
                List<Cell> moveAbleCells = ClientManager.whereToMove(selectedCard);
                moveAbleCells.forEach(moveAbleCell -> {
                    cell[moveAbleCell.getX()][moveAbleCell.getY()].getStyleClass().removeAll("empty-cell");
                    cell[moveAbleCell.getX()][moveAbleCell.getY()].getStyleClass().add("can-move-cell");
                });
            } else {
                List<Cell> putAbleCells = ClientManager.whereToPut(selectedCard);
                for (Cell putAbleCell : putAbleCells) {
                    cell[putAbleCell.getX()][putAbleCell.getY()].getStyleClass().removeAll("empty-cell");
                    cell[putAbleCell.getX()][putAbleCell.getY()].getStyleClass().add("can-insert-cell");
                }
            }
        }
    }

    private void updateHand() {
        Hand hand = ClientManager.getMe().getHand();
        System.out.println(ClientManager.getMe().getAccount().getUsername());
        System.out.println("Hand: \n\t" + hand.getCards().toString());
        int index = 0;
        for (Card card : hand.getCards()) {
            handItemMana[index].setText("" + card.getManaPoint());
            String imageUrl = "/resources/images/cards/" + card.getName() + "_idle.gif";
            if (card instanceof Spell) {
                imageUrl = "/resources/images/cards/" + card.getName() + ".gif";
            }
            handItemImages[index].setImage(new Image(imageUrl));
            int finalIndex = index;
            int finalIndex1 = index;
            handItemContainer[index].setOnMouseClicked(event -> {
                for (AnchorPane handItem : handItemContainer) {
                    handItem.getStyleClass().remove("hand-item-selected");
                }
                if (!card.equals(selectedCard)) {
                    // TODO: check if can select hand card (have enough mana)

                    handItemContainer[finalIndex1].getStyleClass().add("hand-item-selected");
                    selectedCard = card;
                    isSelectedCardInGame = false;
                } else {
                    selectedCard = null;
                }
                updateCells();
            });
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

    private void updateMana() {
        Image mana = new Image("/resources/images/battle/ui/icon_mana@2x.png");
        Image noMana = new Image("/resources/images/battle/ui/icon_mana_inactive@2x.png");
        int mana1 = ClientManager.getPlayingMatch().getPlayer1().getMana();
        int mana2 = ClientManager.getPlayingMatch().getPlayer2().getMana();
        for (int i = 0; i < 9; i++)
            player1Mana[i].setImage(i < mana1 ? mana : noMana);
        for (int i = 0; i < 9; i++)
            player2Mana[i].setImage(i < mana2 ? mana : noMana);

    }

    public void graveyardToggle(MouseEvent mouseEvent) {
        if (!isGraveyardOpen) {
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
        if (column > 4) scale = -1;
        imageView.setScaleX(scale);
        Rectangle rectangle = getCardRectangle(row, column);
        imageView.setFitWidth(rectangle.getWidth());
        imageView.setFitHeight(rectangle.getHeight());
        imageView.setImage(new Image("/resources/images/cards/" + card.getName() + "_idle.gif"));
        anchorPane.relocate(rectangle.getX(), rectangle.getY());

        ImageView attackPointBackground = new ImageView(new Image("/resources/images/battle/ui/icon_atk@2x.png"));
        ImageView healthPointBackground = new ImageView(new Image("/resources/images/battle/ui/icon_hp@2x.png"));

        attackPointBackground.setFitWidth(50);
        attackPointBackground.setFitHeight(50);

        AnchorPane.setLeftAnchor(attackPointBackground, 25.0);
        AnchorPane.setBottomAnchor(attackPointBackground, 10.0);

        healthPointBackground.setFitWidth(50);
        healthPointBackground.setFitHeight(50);

        AnchorPane.setRightAnchor(healthPointBackground, 25.0);
        AnchorPane.setBottomAnchor(healthPointBackground, 10.0);

        Label apLabel = new Label("" + ((Attacker) card).getAttackPoint());
        Label hpLabel = new Label("" + ((Attacker) card).getCurrentHealth());

        apLabel.getStyleClass().add("card-data-in-game-label");
        hpLabel.getStyleClass().add("card-data-in-game-label");

        AnchorPane.setLeftAnchor(apLabel, 35.0);
        AnchorPane.setBottomAnchor(apLabel, 22.0);
        apLabel.setAlignment(Pos.CENTER);
        apLabel.setPrefWidth(30);

        AnchorPane.setRightAnchor(hpLabel, 35.0);
        AnchorPane.setBottomAnchor(hpLabel, 22.0);
        hpLabel.setAlignment(Pos.CENTER);
        hpLabel.setPrefWidth(30);

        anchorPane.setMouseTransparent(true);
        anchorPane.getChildren().addAll(imageView, attackPointBackground, healthPointBackground, hpLabel, apLabel);
        return anchorPane;
    }

    public void endTurn(MouseEvent mouseEvent) {
        playMusic("sfx_ui_select.m4a");
        ClientManager.endTurn();
        updateMana();
        updateHand();
        String AIMove = "";
        if (ClientManager.isAITurn())
            AIMove = ClientManager.getAIMove();
        for (Command command : BattleMenu.getAICommands()) {
            Matcher matcher = command.getPattern().matcher(AIMove);
            if(matcher.find()) {
                Method method ;
                try {
                    method = getClass().getMethod(command.getFunctionName(), Matcher.class);
                    Object object = method.invoke(null, matcher);
                    if(object != null && object.equals(Boolean.FALSE))
                        return;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }



}
