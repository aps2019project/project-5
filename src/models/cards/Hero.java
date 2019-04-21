package models.cards;

import models.cards.spell.Spell;

public class Hero extends Card {
    private int health;
    private int attackPoint;
    private AttackType attackType;
    private int range;
    private Spell specialPower;

    public int getHealth() {
        return health;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }

    public void useSpecialPower() {

    }

    public Hero(String name, String description, int manaPoint, int price, int health, int attackPoint, AttackType attackType, int range) {
        super(name, description, manaPoint, price);
        this.health = health;
        this.attackPoint = attackPoint;
        this.attackType = attackType;
        this.range = range;
    }
}
