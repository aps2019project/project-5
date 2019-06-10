package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class PowerBuff extends Buff {
    private int powerPoint;

    public PowerBuff(int powerPoint, int activeTime, boolean isContinous) {
        super(activeTime, isContinous);
        this.powerPoint = powerPoint;
    }

    public int getPowerPoint() {
        return this.powerPoint;
    }

    @Override
    public void buffEffect(Card card) {
        if (!isContinues) super.activeTime++;
        ((Attacker) card).incrementAP(this.powerPoint);
    }
}
