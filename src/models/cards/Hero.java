package models.cards;

import models.cards.spell.Spell;

public class Hero extends Card {
    private int health;
    private int attackPoint;

    public int getHealth() { return health; }
    public int getAttackPoint() { return attackPoint; }
    public Spell getSpecialPower() { return specialPower; }
    private Spell specialPower;
    public void useSpecialPower() {}
}
