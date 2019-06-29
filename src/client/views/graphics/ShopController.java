package client.views.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import client.controllers.ClientManager;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import client.models.Action;
import client.models.Collection;
import client.models.cards.Attacker;
import client.models.cards.Card;
import client.models.cards.Hero;
import client.models.cards.Minion;
import client.models.cards.spell.Spell;
import client.views.Graphics;
import client.views.SpriteMaker;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static client.views.Graphics.Menu.CUSTOM_CARD;
import static client.views.Graphics.Menu.MAIN_MENU;
import static client.views.Graphics.playMusic;

public class ShopController implements Initializable {
    public JFXMasonryPane cardContainer;
    public ImageView backBtn;
    public TextField searchField;
    public Label filterNone, filterHeroes, filterMinions, filterSpells;
    public Button addCustomCard;
    private Type filterType = Card.class;
    public Label drakes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateCards("", filterType);

        drakes.setText(ClientManager.getAccount().getDrakeString());

        backBtn.setOnMouseClicked(event -> {
            Graphics.playMusic("sfx_ui_select.m4a");
            Graphics.setMenu(MAIN_MENU);
        });

        searchField.textProperty().addListener(((observable, oldValue, newValue) -> updateCards(newValue, filterType)));

        Label[] filterLabels = new Label[]{filterNone, filterHeroes, filterMinions, filterSpells};
        for (Label filterLabel : filterLabels) {
            filterLabel.setOnMouseClicked(event -> {
                Graphics.playMusic("sfx_ui_select.m4a");
                for (Label otherLabel : filterLabels)
                    otherLabel.getStyleClass().remove("selected");
                filterLabel.getStyleClass().add("selected");
                if (filterLabel == filterHeroes) filterType = Hero.class;
                else if (filterLabel == filterMinions) filterType = Minion.class;
                else if (filterLabel == filterSpells) filterType = Spell.class;
                else filterType = Card.class;
                updateCards(searchField.getText(), filterType);
            });
        }
    }

    public static AnchorPane getCardPane(Card card, boolean isInShop) {
        boolean isAttacker = card instanceof Hero || card instanceof Minion;
        AnchorPane cardPane = new AnchorPane();
        cardPane.getStyleClass().add("card-pane");
        if (isAttacker)
            cardPane.getStyleClass().add("attacker-pane");
        else
            cardPane.getStyleClass().add("spell-pane");
        cardPane.setPrefSize(200, 262);

        Label cardName = new Label(card.getName().toUpperCase());
        cardName.relocate(15, 130);
        cardName.setPrefWidth(200);
        cardName.setAlignment(Pos.CENTER);
        cardName.getStyleClass().add("card-name-label");
        cardPane.getChildren().add(cardName);

        String type = card instanceof Minion ? "MINION" : (card instanceof Hero ? "HERO" : "SPELL");
        Label cardType = new Label(type);
        cardType.relocate(15, 150);
        cardType.setPrefWidth(200);
        cardType.setAlignment(Pos.CENTER);
        cardType.getStyleClass().add("card-type-label");
        cardPane.getChildren().add(cardType);

        try {
            Action action = Action.IDLE;
            ImageView imageView = new ImageView();
            if(card instanceof Spell)
                action = Action.SPELL_IDLE;
            SpriteMaker.getAndShowAnimation(imageView, card.getName(), action, 10000);
            if (isAttacker) {
                imageView.relocate(30, -10);
                imageView.setFitWidth(160);
                imageView.setFitHeight(147);
            } else {
                imageView.relocate(55, 25);
                imageView.setFitWidth(120);
                imageView.setFitHeight(120);
            }
            cardPane.getChildren().add(imageView);
        } catch (Exception ignored) {
        }

        if (isAttacker) {
            Label health = new Label("" + ((Attacker) card).getHealth());
            health.getStyleClass().add("shop-card-health");
            if (isInShop)
                health.relocate(157, 163);
            else
                health.relocate(150, 155);
            health.setPrefWidth(30);
            health.setAlignment(Pos.CENTER);

            Label power = new Label("" + ((Attacker) card).getAttackPoint());
            power.getStyleClass().add("shop-card-power");
            if (isInShop)
                power.relocate(38, 163);
            else
                power.relocate(35, 155);
            power.setPrefWidth(30);
            power.setAlignment(Pos.CENTER);

            cardPane.getChildren().addAll(power, health);
        }

        return cardPane;
    }

    private void updateCards(String q, Type type) {
        cardContainer.getChildren().clear();
        List<Card> cards = new ArrayList<>();
        if (q == null || q.equals("")) {
            cards = ClientManager.getShopCollection().getCardsList();
        } else {
            try {
                cards = ClientManager.searchCardInShop(q);
            } catch (Collection.CardNotFoundException ignored) {
            }
        }

        cards.forEach(card -> {
            if (card.getClass() == type || type == Card.class) {
                AnchorPane cardPane = getCardPane(card, true);
                cardPane.setOnMouseClicked(event -> {
                    Graphics.playMusic("sfx_ui_select.m4a");
                    JFXButton buy = new JFXButton("BUY");
                    buy.setLayoutX(110);
                    buy.setLayoutY(210);
                    buy.setPrefSize(90, 62);
                    buy.getStyleClass().addAll("shop-buy-button");
                    JFXButton cancel = new JFXButton("CANCEL");
                    cancel.setLayoutX(25);
                    cancel.setLayoutY(210);
                    cancel.setPrefSize(90, 62);
                    cancel.getStyleClass().addAll("shop-cancel-button");
                    cardPane.getChildren().addAll(buy, cancel);
                    buy.setOnMouseClicked(bought -> {
                        Graphics.playMusic("sfx_ui_select.m4a");
                        try {
                            ClientManager.buy(card.getName());
                        } catch (Exception ignored) {
                        }
                        cardPane.getChildren().removeAll(buy, cancel);

                    });
                    cancel.setOnMouseClicked(canceled -> {
                        Graphics.playMusic("sfx_ui_select.m4a");
                        cardPane.getChildren().removeAll(buy, cancel);
                    });
                });
                cardContainer.getChildren().add(cardPane);
            }
        });
    }

    public void addCustomCard(MouseEvent mouseEvent) {
        playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(CUSTOM_CARD);

    }
}