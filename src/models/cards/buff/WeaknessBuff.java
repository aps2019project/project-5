package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class WeaknessBuff extends Buff {

    private int powerPoint;

    public WeaknessBuff(int powerPoint, int activeTime) {
        super(activeTime);
        this.powerPoint = powerPoint;
    }

    public int getPowerPoint() {
        return this.powerPoint;
    }

    @Override
    public void buffEffect(Card card) {
        ((Attacker) card).decrementAP(this.powerPoint);
    }
}
