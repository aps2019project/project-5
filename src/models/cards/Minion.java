package models.cards;

import models.cards.spell.Spell;

public class Minion extends Card {
    private int health;
    private int attackPoint;
    private Spell specialPower;

    public int getHealth() { return health; }
    public int getAttackPoint() { return attackPoint; }
    public Spell getSpecialPower() { return specialPower; }
}
