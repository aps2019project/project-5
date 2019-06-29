package client.models.cards.buff;

import client.models.cards.Attacker;
import client.models.cards.Card;

public class KillBuff extends Buff {
    public KillBuff(int activeTime, boolean isContinues) {
        super(activeTime, isContinues);
    }

    @Override
    public void buffEffect(Card card) {
        ((Attacker) card).makeBuffClear();
    }
}
