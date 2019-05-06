package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class PoisonBuff extends Buff {
    private int powerPoint;

    public PoisonBuff(int maxActiveTime, int powerPoint, boolean isContinious) {
        super(maxActiveTime, isContinious);
        this.powerPoint = powerPoint;
    }

    public int getPowerPoint() {
        return this.powerPoint;
    }

    @Override
    public void buffEffect(Card card) {
        if (!super.isContinous) super.activeTime++;
        ((Attacker) card).decrementAP(this.powerPoint);
    }
}
