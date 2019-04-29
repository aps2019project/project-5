package models.cards;

import models.cards.spell.Spell;

public class Attacker extends Card {
    private int health;
    private int range;
    private int attackPoint;
    private AttackType attackType;
    private Spell specialPower;

    public Attacker(String name, String description, int manaPoint, int price, int health, int attackPoint,
                    AttackType attackType, int range) {
        super(name, description, manaPoint, price);
        this.health = health;
        this.attackPoint = attackPoint;
        this.attackType = attackType;
        this.range = range;
    }

    public int getHealth() {
        return health;
    }

    public int getRange() {
        return range;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }
}
