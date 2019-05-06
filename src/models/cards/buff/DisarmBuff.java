package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class DisarmBuff extends Buff {
    public DisarmBuff(int activeTime, boolean isContinues) {
        super(activeTime, isContinues);
    }

    @Override
    public void buffEffect(Card card) {
        if (!isContinues) super.activeTime ++;
        ((Attacker) card).setCounterAttackAbility(false);
    }
}
