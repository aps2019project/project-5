package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class DisarmBuff extends Buff {
    public int getActiveTime() {
        return activeTime;
    }

    public DisarmBuff(int activeTime, boolean isContinous) {
        super(activeTime, isContinous);
    }

    @Override
    public void buffEffect(Card card) {
        if (!isContinous) activeTime += 0;
        ((Attacker) card).setCounterAttackAbility(false);
    }
}
