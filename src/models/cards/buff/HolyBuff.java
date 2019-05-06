package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class HolyBuff extends Buff {
    private int healthPoint;

    public HolyBuff(int healthPoint, int activeTime, boolean isContinious) {
        super(activeTime, isContinious);
        this.healthPoint = healthPoint;
    }

    public int getHealthPoint() {
        return this.healthPoint;
    }

    @Override
    public void buffEffect(Card card) {
        if (!isContinues) super.activeTime++;
        ((Attacker) card).decrementHP(this.healthPoint);
    }
}
