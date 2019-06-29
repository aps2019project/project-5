package client.models.cards.buff;

import client.models.cards.Attacker;
import client.models.cards.Card;

public class HolyBuff extends Buff {
    private int healthPoint;

    public HolyBuff(int healthPoint, int activeTime, boolean isContinues) {
        super(activeTime, isContinues);
        this.healthPoint = healthPoint;
    }

    public int getHealthPoint() {
        return this.healthPoint;
    }

    @Override
    public void buffEffect(Card card) {
        if (!isContinues) super.activeTime++;
        ((Attacker) card).incrementHP(this.healthPoint);
    }
}
