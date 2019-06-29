package client.models.cards.buff;

import client.models.cards.Attacker;
import client.models.cards.Card;

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
