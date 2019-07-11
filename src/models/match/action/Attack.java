package models.match.action;

import models.cards.Attacker;

public class Attack extends GameAction {
    public Attacker attacker;
    public Attacker defender;
    public boolean hasCounterAttack;

    public Attack(Attacker attacker, Attacker defender, boolean hasCounterAttack) {
        this.attacker = attacker;
        this.defender = defender;
        this.hasCounterAttack = hasCounterAttack;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "attacker=" + attacker +
                ", defender=" + defender +
                ", hasCounterAttack=" + hasCounterAttack +
                '}';
    }
}
