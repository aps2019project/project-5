package models.cards;

import models.cards.spell.Spell;
import models.cards.spell.TargetType;

public class Minion extends Card {
    private int health;
    private int attackPoint;
    private Spell specialPower;
    private TargetType targetType;

    public int getHealth() {
        return health;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }

    public Minion(int id, String name, String description, int manaPoint, int price, int health, int attackPoint, TargetType targetType) {
        super(id, name, description, manaPoint, price);
        this.health = health;
        this.attackPoint = attackPoint;
        this.targetType = targetType;
    }
}
