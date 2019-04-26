package models;

import models.cards.AttackType;
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;

public class Shop {
    private static Collection allCards = new Collection();

    static {
        // TODO: add cards to allCards Collection.

        allCards.addMember(new Minion(
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

        allCards.addMember(new Minion(
                "Persian Swordsman",
                "",
                2,
                400,
                6,
                4,
                AttackType.MELEE,
                0,
                SpecialPowerActivateTime.ONATTACK
        )); // Special Power must be added!

        allCards.addMember(new Minion(
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
}
