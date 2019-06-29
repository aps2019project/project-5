package client.models.cards.buff;

import client.models.cards.Attacker;
import client.models.cards.Card;

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
        if (!super.isContinues) super.activeTime++;
        ((Attacker) card).decrementHP(this.healthPoint);
    }
}
