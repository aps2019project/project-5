package client.models.cards.buff;

import client.models.cards.Attacker;
import client.models.cards.Card;

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
