package client.models.cards.buff;

import client.models.cards.Attacker;
import client.models.cards.Card;

public class StunBuff extends Buff {
    public StunBuff(int activeTime, boolean isContiniuos) {
        super(activeTime, isContiniuos);
    }

    @Override
    public void buffEffect(Card card) {
        if (!isContinues) super.activeTime++;
        ((Attacker) card).setMoveAbility(false);
        ((Attacker) card).setTurnAttackAvailability(false);
    }
}
