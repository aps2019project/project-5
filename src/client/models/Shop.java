package client.models;

import data.JsonParser;
import client.models.cards.Card;
import client.models.Collection.CardNotFoundException;
import client.models.Collection.ItemsFullException;
import client.models.Account.NotEnoughDrakeException;
import client.models.items.UsableItem;

import java.io.FileNotFoundException;
import java.util.List;


public class Shop {
    private static final int ITEMS_MAX_NUMBER = 3;
    private Collection cardsCollection = new Collection();
    private static Shop shopInstance = null;

    {
        try {
            cardsCollection.addCards(JsonParser.getMinions());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            cardsCollection.addCards(JsonParser.getHeroes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            cardsCollection.addCards(JsonParser.getSpells());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        return cardsCollection.getCardsList(cardName);
    }

    public Card searchCard(String cardName) throws CardNotFoundException {
        return cardsCollection.getCard(cardName);
    }

    public void buy(Account account, String cardName) throws Collection.CollectionException, NotEnoughDrakeException {
        Card card = shopInstance.searchCard(cardName);
        if (account.getDrake() < card.getPrice())
            throw new NotEnoughDrakeException();
        if (card instanceof UsableItem && account.getItemsNumber() == ITEMS_MAX_NUMBER)
            throw new ItemsFullException();
        account.addCardToCollection(card);
        account.incrementDrake(-card.getPrice());
    }


    public void sell(Account account, String name) throws CardNotFoundException {
        Card card = account.getCard(name);
        if (card == null)
            throw new CardNotFoundException();
        account.incrementDrake(card.getPrice());
        account.removeCardFromCollection(card);
    }
}
