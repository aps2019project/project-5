package models;

import models.cards.AttackType;
import models.cards.Card;
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;
import models.Collection.CardNotFoundException;
import models.Collection.ItemsFullException;
import models.Account.NotEnoughDrakeException;

import java.util.List;


public class Shop {
    private Collection cardsCollection = new Collection();
    private static Shop shopInstance = null;

    {
        // TODO: add cards to cardsCollection Collection.

        cardsCollection.addCard(new Minion(
                1,
                "Persian Archer",
                "",
                2,
                300,
                6,
                4,
                AttackType.RANGED,
                7,
                null
        ));

        cardsCollection.addCard(new Minion(
                2,
                "Persian Swordsman",
                "",
                2,
                400,
                6,
                4,
                AttackType.MELEE,
                0,
                SpecialPowerActivateTime.ON_ATTACK
        )); // Special Power must be added!

        cardsCollection.addCard(new Minion(
                3,
                "Persian Lancer",
                "",
                1,
                500,
                5,
                3,
                AttackType.HYBRID,
                3,
                null
        ));
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
