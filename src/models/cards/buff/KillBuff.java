package models.cards.buff;

import models.cards.Attacker;
import models.cards.Card;

public class KillBuff extends Buff {
    public KillBuff(int activeTime, boolean isContinues) {
        super(activeTime, isContinues);
    }

    @Override
    public void buffEffect(Card card) {
        ((Attacker) card).makeBuffClear();
    }
}
