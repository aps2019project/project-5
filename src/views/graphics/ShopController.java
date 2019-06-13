package views.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import controllers.ClientManager;
import controllers.Manager;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.Account;
import models.Collection;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import views.Graphics;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopController implements Initializable {

    public JFXMasonryPane cardContainer;
    public ImageView backBtn;
    public TextField searchField;
    public Label filterNone, filterHeroes, filterMinions, filterSpells;
    public Type filterType = Card.class;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateCards("", filterType);

        backBtn.setOnMouseClicked(event -> Graphics.stage.getScene().setRoot(Graphics.mainMenuRoot));

        searchField.textProperty().addListener(((observable, oldValue, newValue) -> updateCards(newValue, filterType)));

        Label[] filterLabels = new Label[]{filterNone, filterHeroes, filterMinions, filterSpells};
        for (Label filterLabel : filterLabels) {
            filterLabel.setOnMouseClicked(event -> {
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
            Image image;
            if(isAttacker)
                image = new Image("/resources/images/cards/" + card.getName() + "_breathing.gif");
            else
                image = new Image("/resources/images/cards/" + card.getName() + ".gif");
            ImageView imageView = new ImageView(image);
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
        } catch (Exception ignored) {}

        if (isAttacker) {
            Label health = new Label("" + ((Attacker) card).getHealth());
            health.getStyleClass().add("shop-card-health");
            health.relocate(157, 163);
            health.setPrefWidth(30);
            health.setAlignment(Pos.CENTER);

            Label power = new Label("" + ((Attacker) card).getAttackPoint());
            power.getStyleClass().add("shop-card-power");
            power.relocate(38, 163);
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
            } catch (Collection.CardNotFoundException ignored) {}
        }

        cards.forEach(card -> {
            if (card.getClass() == type || type == Card.class) {
                AnchorPane cardPane = getCardPane(card, true);
                cardPane.setOnMouseClicked(event -> {
                    JFXButton buy = new JFXButton("Buy");
                    buy.setText("Buy");
                    buy.setLayoutX(125);
                    buy.setLayoutY(220);
                    buy.getStyleClass().addAll("btn-primary", "btn-lg");
                    JFXButton cancel = new JFXButton("Cancel");
                    cancel.setLayoutX(10);
                    cancel.setLayoutY(220);
                    cancel.getStyleClass().addAll("btn-primary", "btn-lg");
                    cardPane.getChildren().addAll(buy, cancel);
                    buy.setOnMouseClicked(bought -> {
                        try {
                            Manager.buy(card.getName());
                        } catch (Exception ignored) {
                        }
                        cardPane.getChildren().removeAll(buy, cancel);

                    });
                    cancel.setOnMouseClicked(canceled -> {
                        cardPane.getChildren().removeAll(buy, cancel);
                    });
                });
                cardContainer.getChildren().add(cardPane);
            }
        });
    }
}
