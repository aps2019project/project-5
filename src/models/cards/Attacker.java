package models.cards;

import models.map.Cell;

public class Attacker extends Card {
    public int health, currentHealth;
    private int attackPoint;
    public Cell cell;

    public int getAttackPoint() {
        // TODO: apply effect of buffs!!!
        return attackPoint;
    }
}
