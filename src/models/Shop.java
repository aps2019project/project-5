package models;

import models.cards.AttackType;
import models.cards.Card;
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;
import models.exceptions.AccountNotFoundException;
import models.exceptions.CardNotFoundException;


public class Shop {
    private static Collection cardsCollection = new Collection();


    static {
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

    public Collection getCardsCollection() {
        return cardsCollection;
    }


    public void sell(Account account, Card card) throws AccountNotFoundException, CardNotFoundException {

    }
}
