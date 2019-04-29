package models;

import data.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import models.cards.AttackType;
import models.cards.Card;
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;
import models.Collection.CardNotFoundException;
import models.Collection.ItemsFullException;
import models.Account.NotEnoughDrakeException;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.List;


public class Shop {
    private Collection cardsCollection = new Collection();
    private static Shop shopInstance = null;

    {
        try {
            cardsCollection.getCards().addAll(JsonParser.getMinions());
        } catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
        }
        try {
            cardsCollection.getCards().addAll(JsonParser.getHeroes());
        } catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
        }
        System.out.println(cardsCollection.getCards().size());
    }

    public static Shop getInstance() {
        if (shopInstance == null)
            shopInstance = new Shop();
        return shopInstance;
    }

    public Collection getCardsCollection() {
        return cardsCollection;
    }

    public List<Card> searchCards(String cardName) throws CardNotFoundException {
        return cardsCollection.getCards(cardName);
    }

    public Card searchCard(String cardName) throws CardNotFoundException {
        return cardsCollection.getCard(cardName);
    }

    public void buy(Account account, String cardName) throws CardNotFoundException, NotEnoughDrakeException,
            ItemsFullException {
        Card card = shopInstance.searchCard(cardName);
        if (account.getDrake() < card.getPrice())
            throw new NotEnoughDrakeException();
        if (account.getItemsNumber() == 3)
            throw new ItemsFullException();
        account.addCardToCollection(card);
        account.incrementDrake(-card.getPrice());
    }


    public void sell(Account account, int id) throws CardNotFoundException {
        Card card = account.getCard(id);
        if (card == null)
            throw new CardNotFoundException();
        account.incrementDrake(card.getPrice());
        account.removeCardFromCollection(card);
    }
}
