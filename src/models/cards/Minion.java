package models.cards;

import models.cards.spell.SpecialPowerActivateTime;

public class Minion extends Attacker {
    private SpecialPowerActivateTime specialPowerActivateTime;

    public Minion(String name, String description, int manaPoint, int price, int health, int attackPoint, AttackType attackType, int range, SpecialPowerActivateTime specialPowerActivateTime) {
        super(name, description, manaPoint, price, health, attackPoint, attackType, range);
        this.specialPowerActivateTime = specialPowerActivateTime;
    }

    public SpecialPowerActivateTime getSpecialPowerActivateTime() {
        return specialPowerActivateTime;
    }
}
