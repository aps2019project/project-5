package server;

import models.cards.AttackType;
import models.cards.Card;
import models.cards.Collection;
import models.cards.Minion;

public class Test {
    public static void main(String... args) {
        Collection shop = new Collection();
        shop.cards.put(new Minion("Persian Archer", "", 300, 2, 6, 4, AttackType.RANGED, 7), 10);
        shop.cards.put(new Minion("Persian Swordsman", "", 400, 2, 6, 4, AttackType.MELEE, 1), 10);
        shop.cards.put(new Minion("Persian Lancer", "", 500, 1, 5, 3, AttackType.HYBRID, 3), 10);
        shop.cards.put(new Minion("Persian Horse Rider", "", 200, 4, 10, 6, AttackType.MELEE, 1), 10);
        shop.cards.put(new Minion("Persian Champion", "", 600, 9, 24, 6, AttackType.MELEE, 1), 10);
        shop.cards.put(new Minion("Persian General", "", 800, 7, 12, 4, AttackType.RANGED, 5), 10);
        shop.cards.put(new Minion("Turan Archer", "", 500, 1, 3, 4, AttackType.RANGED, 5), 10);
        shop.cards.put(new Minion("Turan Sling", "", 600, 1, 4, 2, AttackType.RANGED, 7), 10);
        shop.cards.put(new Minion("Turan Lancer", "", 600, 1, 4, 4, AttackType.HYBRID, 3), 10);
        shop.cards.put(new Minion("Turan Spy", "", 700, 4, 6, 6, AttackType.MELEE, 1), 10);
        shop.cards.put(new Minion("Turan Wand", "", 450, 2, 3, 10, AttackType.MELEE, 1), 10);
        shop.cards.put(new Minion("Turan Prince", "", 800, 6, 6, 10, AttackType.MELEE, 1), 10);
        shop.cards.put(new Minion("Black Demon", "", 300, 9, 14, 10, AttackType.HYBRID, 7), 10);
        shop.cards.put(new Minion("Giant Catapult", "", 300, 9, 12, 12, AttackType.RANGED, 7), 10);
        shop.cards.put(new Minion("Small Tiger", "", 200, 1, 2, 2, AttackType.RANGED, 3), 10);
        shop.cards.put(new Minion("Hog Head Demon", "", 300, 6, 16, 8, AttackType.MELEE, 1), 10);
        shop.cards.put(new Minion("One Eye Giant", "", 500, 7, 12, 11, AttackType.HYBRID, 3), 10);
        shop.cards.put(new Minion("Avalanche", "", 300, 4, 5, 6, AttackType.RANGED, 4), 10);
        shop.cards.put(new Minion("Fire Dragon", "", 250, 5, 9, 5, AttackType.RANGED, 4), 10);
        shop.cards.put(new Minion("Predator Lion", "", 600, 2, 1, 8, AttackType.MELEE, 1), 10);
    }
}

