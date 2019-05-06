package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class StunBuff extends Buff {
    public StunBuff(int activeTime) {
        super(activeTime);
    }

    @Override
    public void buffEffect(Card card) {
        card.setMoveAvailable(false);
        ((Attacker) card).setTurnAttackAvailability(false);
    }
}
