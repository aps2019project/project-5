package server;

import models.cards.*;
import server.data.DataReader;
import server.data.DataWriter;
import server.data.Files;

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

        shop.cards.put(new Hero("White Daemon", "", 8000, 1, 50, 4, AttackType.MELEE, 1), 10);
        shop.cards.put(new Hero("Phoenix", "", 9000, 5, 50, 4, AttackType.MELEE, 1), 10);
        shop.cards.put(new Hero("SevenHead Dragon", "", 8000, 0, 50, 4, AttackType.MELEE, 1), 10);
        shop.cards.put(new Hero("Rakhsh", "", 8000, 1, 50, 4, AttackType.MELEE, 1), 10);
        shop.cards.put(new Hero("Zahak", "", 10000, 1, 50, 2, AttackType.MELEE, 1), 10);
        shop.cards.put(new Hero("Kaveh", "", 8000, 1, 50, 4, AttackType.MELEE, 1), 10);
        shop.cards.put(new Hero("Arash", "", 10000, 2, 30, 2, AttackType.MELEE, 1), 10);
        shop.cards.put(new Hero("Legend", "", 11000, 1, 50, 3, AttackType.RANGED, 3), 10);
        shop.cards.put(new Hero("Esfandiar", "", 12000, 1, 35, 3, AttackType.HYBRID, 3), 10);
        shop.cards.put(new Hero("Rostam", "", 8000, 1, 55, 7, AttackType.RANGED, 4), 10);

        shop.cards.put(new Spell("Total Disarm", "", 1000, 0, TargetType.AN_OPPONENT_FORCE), 10);
        shop.cards.put(new Spell("Area Dispel", "", 1500, 2, TargetType.SQUARE2x2), 10);
        shop.cards.put(new Spell("Empower", "", 250, 1, TargetType.MY_FORCE), 10);
        shop.cards.put(new Spell("Fireball", "", 400, 1, TargetType.AN_OPPONENT_FORCE), 10);
        shop.cards.put(new Spell("God strength", "", 450, 2, TargetType.MY_HERO), 10);
        shop.cards.put(new Spell("Fireball", "", 600, 3, TargetType.ANY), 10);
        shop.cards.put(new Spell("Lighting Bolt", "", 1250, 2, TargetType.OPPONENT_HERO), 10);
        shop.cards.put(new Spell("Poison Lake", "", 900, 5, TargetType.ANY), 10);
        shop.cards.put(new Spell("Madness", "", 650, 0, TargetType.MY_FORCE), 10);
        shop.cards.put(new Spell("All DisarmBuff", "", 2000, 9, TargetType.ALL_OPPONENT_FORCE), 10);
        shop.cards.put(new Spell("Dispel", "", 2500, 0, TargetType.OPPONENT_FORCE_OR_MY_FORCE), 10);
        shop.cards.put(new Spell("Health With Profit", "", 2250, 0, TargetType.MY_FORCE), 10);
        shop.cards.put(new Spell("Power Up", "", 2500, 2, TargetType.MY_FORCE), 10);
        shop.cards.put(new Spell("All Power", "", 2500, 2, TargetType.ALL_MY_FORCE), 10);
        shop.cards.put(new Spell("All Attack", "", 1500, 4, TargetType.ALL_OPPONENT_FORCE_IN_ONE_COLUMN), 10);
        shop.cards.put(new Spell("Weakening", "", 1000, 1, TargetType.AN_OPPONENT_MINION), 10);
        shop.cards.put(new Spell("Kings Guard", "", 1750, 9, TargetType.ANY), 10);
        shop.cards.put(new Spell("Shock", "", 1200, 9, TargetType.OPPONENT_HERO), 10);

        DataWriter.saveData(Files.CARD_DATA, shop);
        System.out.println(DataReader.getShopCollection().cards);
    }
}

