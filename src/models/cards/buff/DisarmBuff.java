package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class DisarmBuff extends Buff {
    public DisarmBuff(int activeTime, boolean isContinous) {
        super(activeTime, isContinous);
    }

    @Override
    public void buffEffect(Card card) {
        if (!isContinous) super.activeTime ++;
        ((Attacker) card).setCounterAttackAbility(false);
    }
}
