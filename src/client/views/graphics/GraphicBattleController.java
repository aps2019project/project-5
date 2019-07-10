package client.views.graphics;

import client.controllers.BattleClient;
import client.models.Action;
import client.models.Timer;
import client.views.Graphics;
import client.views.SpriteMaker;
import client.views.menus.BattleMenu;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
import models.Response;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Spell;
import models.map.Cell;
import models.map.Map;
import models.match.action.*;

import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static client.views.Graphics.alert;
import static client.views.Graphics.playMusic;

public class GraphicBattleController implements Initializable {


    public Button endTurnBtn;
    public static int token;
    public Label timerLbl;
    public HBox handBox;
    private int eachTurnTime = 20;
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
    private HashMap<Card, AnchorPane> handViews = new HashMap<>();
    private Card selectedCard;
    private BattleMenu battleMenu = new BattleMenu();
    private boolean isSelectedCardInGame = false;
    private Timer timer;

    int speed = 1;
    private ImageView draggingCard = new ImageView();
    private double draggingX;
    private double draggingY;
    public static Thread watchThread;

    private void updateHp(Card... card) {
        for (Card card1 : card) {
            Label hp = (Label) cardViews.get(card1).getChildren().get(3);
            hp.setText("" + ((Attacker) card1).currentHealth);
        }
    }

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

        root.getChildren().add(draggingCard);

        createMapCells();
        copyHandViewsToArray();
        updateHand();
        showProfiles();
        updateMana();
        player1Name.setText(BattleClient.playingMatch.players[0].account.username.toUpperCase());
        player2Name.setText(BattleClient.playingMatch.players[1].account.username.toUpperCase());
        player1Name.setRotate(-3.0);
        player2Name.setRotate(3.0);
        mana1BarContainer.setRotate(-3.0);
        mana2BarContainer.setRotate(3.0);
        showCardsInBoard();
        handItem0_container.setLayoutX(-100);
        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case M:
//                    ClientManager.setMana(10000);
//                    updateMana();
                    break;
                case EQUALS:
                    speed++;
                    break;
                case MINUS:
                    if (speed > 1)
                        speed--;
                    break;
            }
            showCardsInBoard();
            updateHand();
        });
        endTurnBtn.setDisable(!BattleClient.isMyTurn());

        ScheduledThreadPoolExecutor opponentCheck = new ScheduledThreadPoolExecutor(1);
        opponentCheck.scheduleAtFixedRate(this::updateMatch, 0, 1, TimeUnit.SECONDS);

    }

    private void showCardsInBoard() {
        root.getChildren().removeAll(cardViews.values());
        cardViews.clear();

        Map map = BattleClient.playingMatch.map;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                Cell cell = map.cell[i][j];
                Attacker attacker = cell.attacker;
                if (attacker == null)
                    continue;
                AnchorPane cardAnchorPane = getCardInGame(attacker, i, j);
                cardViews.put(attacker, cardAnchorPane);
                setCard(cardAnchorPane);
            }
    }

    private Rectangle getCardRectangle(int row, int column) {
        double x = 483 + column * 97 + (column - 4) * row * 2.5;
        double y = 240 + 90 * row;
        double width = 160 + row * 8;
        double height = 160 + row * 8;
        return new Rectangle(x, y, width, height);
    }

    private void setCard(AnchorPane cardAnchorPane) {
        root.getChildren().add(cardAnchorPane);
    }

    private void showProfiles() {
        Set<Card> heros1 = BattleClient.playingMatch.players[0].deck.filter(Hero.class, "").keySet();
        Set<Card> heros2 = BattleClient.playingMatch.players[0].deck.filter(Hero.class, "").keySet();
        AtomicReference<String> player1HeroName = new AtomicReference<>("Rostam");
        AtomicReference<String> player2HeroName = new AtomicReference<>("Rostam");
        heros1.forEach(hero -> player1HeroName.set(hero.name));
        heros2.forEach(hero -> player2HeroName.set(hero.name));
        player1ProfileImage.setImage(new Image(
                "/client/resources/sprites/HeroLogos/" + player1HeroName.get() + ".png"
        ));
        player2ProfileImage.setImage(new Image(
                "/client/resources/sprites/HeroLogos/" + player2HeroName.get() + ".png"
        ));
    }

    private int getDistance(double x1, double y1, double x2, double y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void moveCard(AnchorPane cardPane, Rectangle newPosition, Card card) {
        int time = getDistance(newPosition.getX(), newPosition.getY(), cardPane.getLayoutX(), cardPane.getLayoutY()) * 7;

        Timeline timeline = new Timeline();
        KeyFrame end = new KeyFrame(new Duration(time),
                new KeyValue(cardPane.layoutXProperty(), newPosition.getX()),
                new KeyValue(cardPane.layoutYProperty(), newPosition.getY()));
        timeline.getKeyFrames().add(end);

        timeline.play();

        new Thread(() -> {
            ImageView imageView = (ImageView) cardPane.getChildren().get(0);
            Graphics.playMusic("sfx_unit_run_charge_4.m4a");
            SpriteMaker.getAndShowAnimation(imageView, card.name, Action.RUN, 1000, speed);
            long newTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - newTime <= time) {
            }
            SpriteMaker.getAndShowAnimation(imageView, card.name, Action.IDLE, 10000000, speed);
        }).start();
        BattleClient.updatePlayingMatch();
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
            if (cardView.getKey() instanceof Attacker) {
                if (((Attacker) cardView.getKey()).cell.x == x &&
                        ((Attacker) cardView.getKey()).cell.y == y)
                    return cardView.getKey();
            }
        return null;
    }

    private void attack(Card myCard, Card enemyCard) {
        AnchorPane myAnchor = cardViews.get(myCard);
        AnchorPane enemyAnchor = cardViews.get(enemyCard);
        ImageView myImageView = (ImageView) myAnchor.getChildren().get(0);
        ImageView enemyImageView = (ImageView) enemyAnchor.getChildren().get(0);
        new Thread(() -> {
            double actionTime = SpriteMaker.getAnimationTime(myImageView, myCard.name, Action.ATTACK, 1, speed);
            Graphics.playMusic("sfx_f3_general_attack_impact.m4a");
            SpriteMaker.getAndShowAnimation(myImageView, myCard.name, Action.ATTACK, 1, speed);
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time <= actionTime) {
            }
            SpriteMaker.getAndShowAnimation(myImageView, myCard.name, Action.IDLE, 10000000, speed);
            Platform.runLater(() -> updateHp(enemyCard));

            actionTime = SpriteMaker.getAnimationTime(enemyImageView, enemyCard.name, Action.ATTACK, 1, speed);
            time = System.currentTimeMillis();
            SpriteMaker.getAndShowAnimation(enemyImageView, enemyCard.name, Action.ATTACK, 1, speed);
            Graphics.playMusic("sfx_f3_general_attack_swing.m4a");
            while (System.currentTimeMillis() - time <= actionTime) {
            }

            SpriteMaker.getAndShowAnimation(enemyImageView, enemyCard.name, Action.IDLE, 10000000, speed);
            SpriteMaker.getAndShowAnimation(enemyImageView, enemyCard.name, Action.IDLE, 10000000, speed);
            Platform.runLater(() -> updateHp(myCard));
        }).start();

    }

    private void clickCell(int row, int column) {
        if (!BattleClient.isMyTurn()) return;
        Card clickedCard = getCardInCell(row, column);
        if (selectedCard == null) {
            if (clickedCard != null) {
                BattleClient.selectCard(clickedCard.id);
                selectedCard = clickedCard;
                isSelectedCardInGame = true;
            }
        } else {
            if (selectedCard.equalsInGame(clickedCard)) {
                selectedCard = null;
                System.out.println("Card unselected");
            } else {
                if (isSelectedCardInGame) {
                    if (clickedCard == null) {
                        if (BattleClient.move(row, column)) {
                            moveCard(cardViews.get(selectedCard), getCardRectangle(row, column), selectedCard);
                            if (selectedCard instanceof Attacker) {
                                ((Attacker) selectedCard).cell.x = row;
                                ((Attacker) selectedCard).cell.y = column;
                            }
                        } else {
                            System.out.println("can't move here");
                        }
                    } else {
                        System.out.format("Selected card player name: %s", BattleClient.getMe().account.username);
                        System.out.format("Clicked card player name: %s", clickedCard.playerName);
                        if (clickedCard.playerName.equals(BattleClient.getMe().account.username)) {
                            System.out.println("card selection changed");
                            BattleClient.selectCard(clickedCard.id);
                            selectedCard = clickedCard;
                            isSelectedCardInGame = true;
                        } else {
                            System.out.println("attack");
                            Attacker attacker = (Attacker) clickedCard;
                            Response response = BattleClient.attack(row, column);
                            attack(selectedCard, clickedCard);
                        }
                    }
                } else {
                    Response response = BattleClient.insert(row, column);
                    if (response.OK) {
                        insertCard(row, column);
                        AnchorPane handAnchorPane = handViews.get(selectedCard);
                        handAnchorPane.getStyleClass().removeAll("hand-item-selected");
                        ((ImageView) handAnchorPane.getChildren().get(0)).setImage(null);
                        ((Label) handAnchorPane.getChildren().get(2)).setText("");
                        handAnchorPane.setOnMouseClicked(event -> {
                        });
                        handAnchorPane.setOnMouseEntered(event -> {
                        });
                        selectedCard = null;
                    }
                }
            }
        }
        updateCells();
    }

    private void updateMatch() {
        GameAction action = BattleClient.getAction();
        if (action instanceof EndTurn) {
            System.out.println(action);
            Platform.runLater(() -> endTurnBtn.setDisable(false));
            BattleClient.updatePlayingMatch();
        }
        if (action instanceof Insert) {
            System.out.println(action);
            Insert insert = (Insert) action;
            selectedCard = insert.card;
            System.out.printf("Selected card is: %s\n", selectedCard);
            Platform.runLater(() -> insertCard(insert.cell.x, insert.cell.y));
            selectedCard = null;
            BattleClient.updatePlayingMatch();
        }
        if (action instanceof Move) {
            System.out.println(action);
            Move move = (Move) action;
            AnchorPane cardPane = cardViews.get(move.card);
            Rectangle newPosition = getCardRectangle(move.newCell.x, move.newCell.y);
            Platform.runLater(() -> moveCard(cardPane, newPosition, move.card));
            BattleClient.updatePlayingMatch();
        }
        if (action instanceof Attack) {
            System.out.println(action);
            Attack attack = (Attack) action;
            // TODO: implement
            BattleClient.updatePlayingMatch();
        }
    }

    private void insertCard(int row, int column) {
        if (selectedCard instanceof Attacker) {
            AnchorPane cardPane = getCardInGame(selectedCard, row, column);
            System.out.println("Card " + selectedCard + " insert!!");
            System.out.println("in " + row + ", " + column);
            cardViews.put(selectedCard, cardPane);
            AnchorPane teleport = new AnchorPane(SpriteMaker.getAndShowAnimation(new ImageView(), "teleport", Action.TELEPORT, 1, speed),
                    SpriteMaker.getAndShowAnimation(new ImageView(), "teleport1", Action.TELEPORT, 1, speed),
                    SpriteMaker.getAndShowAnimation(new ImageView(), "teleport2", Action.TELEPORT, 1, speed),
                    SpriteMaker.getAndShowAnimation(new ImageView(), "teleport3", Action.TELEPORT, 1, speed),
                    SpriteMaker.getAndShowAnimation(new ImageView(), "teleport4", Action.TELEPORT, 1, speed));
            Rectangle rect = getCardRectangle(row - 1, column - 1);
            teleport.setLayoutX(rect.getX() + 150);
            teleport.setLayoutY(rect.getY() + 140);
            teleport.setScaleX(2);
            teleport.setScaleY(2);
            teleport.setMouseTransparent(true);

            setCard(cardPane);
            setCard(teleport);
        } else {

        }
        BattleClient.updatePlayingMatch();
        updateMana();
    }

    private void removeCard(AnchorPane cardPane) {
        root.getChildren().remove(cardPane);
    }

    private void updateCells() {
        BattleClient.updatePlayingMatch();
        String[] removingStyleClassList = {"selected-card-cell", "can-insert-cell", "can-move-cell"};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                cell[i][j].getStyleClass().removeAll(removingStyleClassList);
                cell[i][j].getStyleClass().add("empty-cell");
            }
        }
        if (selectedCard != null) {
            if (isSelectedCardInGame) {
                if (selectedCard instanceof Attacker) {
                    Attacker selectedAttacker = (Attacker) selectedCard;
                    cell[selectedAttacker.cell.x][selectedAttacker.cell.y].getStyleClass().add("selected-card-cell");
                    Set<Cell> moveableCells = BattleClient.getAvailableCells();
                    moveableCells.forEach(moveableCell -> {
                        cell[moveableCell.x][moveableCell.y].getStyleClass().removeAll("empty-cell");
                        cell[moveableCell.x][moveableCell.y].getStyleClass().add("can-move-cell");
                    });
                }
            } else {
                Set<Cell> putableCells = BattleClient.getAvailableCells();
                for (Cell putableCell : putableCells) {
                    cell[putableCell.x][putableCell.y].getStyleClass().removeAll("empty-cell");
                    cell[putableCell.x][putableCell.y].getStyleClass().addAll("can-insert-cell");
                }
            }
        }
    }

    private void updateHand() {
        BattleClient.updatePlayingMatch();
        List<Card> hand = BattleClient.getMe().hand;
        int index = 0;
        for (Card card : hand) {
            if (index == 5) return;

            handItemMana[index].setText("" + card.manaPoint);
            ImageView cardAnimation = SpriteMaker.getAndShowAnimation(
                    handItemImages[index], card.name,
                    card instanceof Spell ? Action.SPELL_IDLE : Action.IDLE, 1000000, speed);
            handItemImages[index].setImage(cardAnimation.getImage());
            if (card instanceof Spell) {
                cardAnimation.setFitWidth(120);
                cardAnimation.setFitHeight(120);
                AnchorPane.setLeftAnchor(cardAnimation, 30.0);
                AnchorPane.setRightAnchor(cardAnimation, 30.0);
                AnchorPane.setTopAnchor(cardAnimation, 30.0);
            } else {
                cardAnimation.setFitWidth(150);
                cardAnimation.setFitHeight(150);
                AnchorPane.setLeftAnchor(cardAnimation, 15.0);
            }

            int finalIndex = index;
            int finalIndex1 = index;
            handItemContainer[index].setOnMouseClicked(event -> {
                for (AnchorPane handItem : handItemContainer) {
                    handItem.getStyleClass().remove("hand-item-selected");
                }
                if (!card.equalsInGame(selectedCard)) {

                    BattleClient.selectCard(card.id);

                    handItemContainer[finalIndex1].getStyleClass().add("hand-item-selected");
                    selectedCard = card;
                    isSelectedCardInGame = false;
                } else {
                    selectedCard = null;
                }
                updateCells();
            });
            handItemContainer[index].setOnMouseEntered(event -> {
                AnchorPane cardPane = ShopController.getCardPane(card, false, 0);
                cardPane.relocate(handItemContainer[finalIndex].localToScene(
                        handItemContainer[finalIndex].getBoundsInLocal()
                ).getMinX(), 600);
                root.getChildren().add(cardPane);
                handItemContainer[finalIndex].setOnMouseExited(event1 -> root.getChildren().remove(cardPane));
            });

            cardAnimation.setOnMousePressed(event -> {
                SpriteMaker.getAndShowAnimation(draggingCard, card.name,
                        card instanceof Spell ? Action.SPELL_IDLE : Action.IDLE, 100, speed);
                draggingCard.setScaleX(1.5);
                draggingCard.setScaleY(1.5);
                draggingCard.setLayoutX(handItemContainer[finalIndex].getLayoutX() + handBox.getLayoutX() + 50);
                draggingCard.setLayoutY(handItemContainer[finalIndex].getLayoutY() + handBox.getLayoutY() + 20);
                draggingX = event.getSceneX();
                draggingY = event.getSceneY();
                draggingCard.setVisible(true);
            });

            cardAnimation.setOnMouseDragged(e -> {
                draggingCard.setTranslateX(e.getSceneX() - draggingX);
                draggingCard.setTranslateY(e.getSceneY() - draggingY);
            });

            cardAnimation.setOnMouseReleased(event -> {
                draggingCard.setVisible(false);
                // TODO: 7/9/19 insert card
            });
            handViews.put(card, handItemContainer[index]);
            index++;
        }
    }


    private void updateMana() {
        BattleClient.updatePlayingMatch();
        Image mana = new Image("/client/resources/images/battle/ui/icon_mana@2x.png");
        Image noMana = new Image("/client/resources/images/battle/ui/icon_mana_inactive@2x.png");
        int mana1 = BattleClient.playingMatch.players[0].manaPoint;
        int mana2 = BattleClient.playingMatch.players[1].manaPoint;
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
        anchorPane.setMouseTransparent(true);
        imageView.setMouseTransparent(true);
        SpriteMaker.getAndShowAnimation(imageView, card.name, Action.IDLE, 1000000, speed);
        int scale = 1;
        if (column > 4) scale = -1;
        imageView.setScaleX(scale);
        Rectangle rectangle = getCardRectangle(row, column);
        imageView.setFitWidth(rectangle.getWidth());
        imageView.setFitHeight(rectangle.getHeight());
        anchorPane.relocate(rectangle.getX(), rectangle.getY());

        ImageView attackPointBackground = new ImageView(new Image("/client/resources/images/battle/ui/icon_atk@2x.png"));
        ImageView healthPointBackground = new ImageView(new Image("/client/resources/images/battle/ui/icon_hp@2x.png"));

        attackPointBackground.setFitWidth(50);
        attackPointBackground.setFitHeight(50);

        AnchorPane.setLeftAnchor(attackPointBackground, 25.0);
        AnchorPane.setBottomAnchor(attackPointBackground, 10.0);

        healthPointBackground.setFitWidth(50);
        healthPointBackground.setFitHeight(50);

        AnchorPane.setRightAnchor(healthPointBackground, 25.0);
        AnchorPane.setBottomAnchor(healthPointBackground, 10.0);

        Label apLabel = new Label("");
        Label hpLabel = new Label("");
        apLabel.setText("" + ((Attacker) card).getAttackPoint());
        hpLabel.setText("" + ((Attacker) card).currentHealth);
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

        anchorPane.getChildren().addAll(imageView, attackPointBackground, healthPointBackground, hpLabel, apLabel);

        System.out.printf("card %s anchor pane created!\n", card);

        return anchorPane;
    }

    public void endTurn(MouseEvent mouseEvent) {
        playMusic("sfx_ui_select.m4a");
        selectedCard = null;
        Response response = BattleClient.endTurn();
        if (response.OK) {
            updateMana();
            updateHand();
            updateCells();
            endTurnBtn.setDisable(true);
        } else {
            alert("error", "error", response.message);
        }
    }
}
