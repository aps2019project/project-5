package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class WeaknessBuff extends Buff {

    private int powerPoint;

    public WeaknessBuff(int powerPoint, int activeTime, boolean isContinous) {
        super(activeTime,isContinous);
        this.powerPoint = powerPoint;
    }

    public int getPowerPoint() {
        return this.powerPoint;
    }

    @Override
    public void buffEffect(Card card) {
        if (!isContinues) super.activeTime++;
        ((Attacker) card).decrementAP(this.powerPoint);
    }
}
