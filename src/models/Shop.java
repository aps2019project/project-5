package models;

import models.cards.AttackType;
import models.cards.Card;
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;
import models.exceptions.AccountNotFoundException;
import models.exceptions.CardNotFoundException;

import java.util.List;


public class Shop {
    private Collection cardsCollection = new Collection();
    private static Shop shopInstance = null;

     {
        // TODO: add cards to cardsCollection Collection.

        cardsCollection.addMember(new Minion(
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

        cardsCollection.addMember(new Minion(
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

        cardsCollection.addMember(new Minion(
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

    public Card searchCard (String cardName) throws CardNotFoundException {
        for (MarketObject mo: cardsCollection.getCards()) {
            Card card = (Card) mo;
            if(card.getName().equals(cardName))
                return card;
        }
        throw new CardNotFoundException("Card not exists in shop.");
    }

    public void sell(Account account, Card card) throws AccountNotFoundException, CardNotFoundException {

    }
}
