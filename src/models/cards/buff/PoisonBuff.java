package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class PoisonBuff extends Buff {
    private int healthPoint;

    public PoisonBuff(int maxActiveTime, int healthPoint, boolean isContinues) {
        super(maxActiveTime, isContinues);
        this.healthPoint = healthPoint;
    }
    
    public int getHealthPoint() {
        return this.healthPoint;
    }

    @Override
    public void buffEffect(Card card) {
        if (!super.isContinous) super.activeTime++;
        ((Attacker) card).decrementHP(this.healthPoint);
    }
}
