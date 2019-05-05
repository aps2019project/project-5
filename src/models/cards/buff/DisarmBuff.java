package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class DisarmBuff extends Buff {
    @Override
    public void buffEffect(Card card) {
        ((Attacker) card).setCounterAttackAbility(false);
    }
}
