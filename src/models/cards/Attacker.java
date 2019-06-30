package models.cards;

import models.map.Cell;

public class Attacker extends Card {
    public int health, currentHealth = 0;
    private int attackPoint;
    public int attackRange;
    public AttackType attackType;
    public Cell cell;

    public int getAttackPoint() {
        // TODO: apply effect of buffs!!!
        return attackPoint;
    }

    public Attacker(String name, String description, int price, int manaPoint, int health, int attackPoint, AttackType attackType, int attackRange) {
        super(name, description, price, manaPoint);
        this.health = health;
        this.attackPoint = attackPoint;
        this.attackType = attackType;
        this.attackRange = attackRange;
    }
}
