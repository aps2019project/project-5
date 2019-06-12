package views.graphics;

import com.jfoenix.controls.JFXMasonryPane;
import controllers.ClientManager;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import models.Collection;
import models.cards.Card;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopController implements Initializable {

    public JFXMasonryPane cardContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateCards("");
    }

    private void updateCards(String q) {
        List<Card> cards = new ArrayList<>();
        if(q == null || q.equals("")) {
            cards = ClientManager.getShopCollection().getCardsList();
        } else {
            try {
                cards = ClientManager.searchCardInShop(q);
            } catch (Collection.CardNotFoundException ignored) {}
        }

        cards.forEach(card -> {
            AnchorPane cardPane = new AnchorPane();
            System.out.println(card.getName());
            cardPane.getStyleClass().add("card-pane");
            cardPane.setPrefSize(200, 262);
            cardContainer.getChildren().add(cardPane);
        });
    }
}
