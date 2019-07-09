package models.match.action;

import models.cards.Attacker;

public class Attack extends GameAction {
    public Attacker attacker;
    public Attacker Defender;
    public boolean hasCounterAttack;

    public Attack(Attacker attacker, Attacker defender, boolean hasCounterAttack) {
        this.attacker = attacker;
        Defender = defender;
        this.hasCounterAttack = hasCounterAttack;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "attacker=" + attacker +
                ", Defender=" + Defender +
                ", hasCounterAttack=" + hasCounterAttack +
                '}';
    }
}
