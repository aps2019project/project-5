package controllers;

import models.Collection;
import models.cards.AttackType;
import models.cards.Minion;

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
                7
        ));

        allCards.addMember(new Minion(
                "Persian Swordsman",
                "",
                2,
                400,
                6,
                4,
                AttackType.MELEE,
                0
        )); // Special Power must be added!

        allCards.addMember(new Minion(
                "Persian Lancer",
                "",
                1,
                500,
                5,
                3,
                AttackType.HYBRID,
                3
        ));
    }
}
