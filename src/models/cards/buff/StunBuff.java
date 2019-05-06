package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class StunBuff extends Buff {
    public StunBuff(int activeTime, boolean isContiniuos) {
        super(activeTime, isContiniuos);
    }

    @Override
    public void buffEffect(Card card) {
        if (!isContinues) super.activeTime++;
        card.setMoveAvailable(false);
        ((Attacker) card).setTurnAttackAvailability(false);
    }
}
