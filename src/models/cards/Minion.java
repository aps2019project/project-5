package models.cards;

import models.cards.spell.SpecialPowerActivateTime;
import models.cards.spell.Spell;

public class Minion extends Attacker {
    private SpecialPowerActivateTime specialPowerActivateTime;

    public Minion(String name, String description, int manaPoint, int price, int health, int attackPoint, AttackType attackType, int range) {
        super(name, description, manaPoint, price, health, attackPoint, attackType, range);
    }

    public SpecialPowerActivateTime getSpecialPowerActivateTime() {
        return specialPowerActivateTime;
    }
}
