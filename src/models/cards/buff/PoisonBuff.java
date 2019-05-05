package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class PoisonBuff extends Buff {
    private int powerPoint;

    public PoisonBuff(int healthPoint) {
        this.powerPoint = healthPoint;
    }

    public int getPowerPoint() {
        return this.powerPoint;
    }

    @Override
    public void buffEffect(Card card) {
        ((Attacker) card).decrementAP(this.powerPoint);
    }
}
