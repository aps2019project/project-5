package models;

import models.cards.AttackType;
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;

public class Shop {
    private static Collection cards = new Collection();

    static {
        // TODO: add cards to cards Collection.

        cards.addMember(new Minion(
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

        cards.addMember(new Minion(
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

        cards.addMember(new Minion(
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

    public static Collection getCards() {
        return cards;
    }
}
