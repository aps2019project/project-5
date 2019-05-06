package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class PoisonBuff extends Buff {
    private int powerPoint;

    public PoisonBuff(int maxActiveTime, int powerPoint) {
        super(maxActiveTime);
        this.powerPoint = powerPoint;
    }

    public int getPowerPoint() {
        return this.powerPoint;
    }

    @Override
    public void buffEffect(Card card) {
        super.activeTime++;
        ((Attacker) card).decrementAP(this.powerPoint);
    }
}
