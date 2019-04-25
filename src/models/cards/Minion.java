package models.cards;

import models.cards.spell.SpecialPowerActivateTime;
import models.cards.spell.Spell;
import models.cards.spell.TargetType;

public class Minion extends Card {
    private int health;
    private int attackPoint;
    private int range;
    private Spell specialPower;
    private SpecialPowerActivateTime specialPowerActivateTime;
    private AttackType attackType;


    public int getHealth() {
        return health;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }

    public Minion(String name, String description, int manaPoint, int price, int health, int attackPoint, AttackType attackType, int range) {
        super(name, description, manaPoint, price);
        this.health = health;
        this.attackPoint = attackPoint;
        this.attackType = attackType;
        this.range = range;
    }
}
