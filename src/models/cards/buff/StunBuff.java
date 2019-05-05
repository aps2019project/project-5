package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class StunBuff extends Buff {
    @Override
    public void buffEffect(Card card) {
        card.setMoveAvailable(false);
        ((Attacker) card).setTurnAttackAvailability(false);
    }
}
