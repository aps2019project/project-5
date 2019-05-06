package models.cards.buff;

import models.cards.Card;
import models.map.Cell;
import models.match.Match;

public abstract class Buff {

    int activeTime;
    private int maxActiveTime;
    boolean isContinous;

    public Buff(int maxActiveTime, boolean isContinous) {
        this.maxActiveTime = maxActiveTime;
        this.isContinous = isContinous;
    }

    public abstract void buffEffect(Card card);

    public static class CardIsNotAvailableException extends Exception {
        public CardIsNotAvailableException() {
            super("Card is not available");
        }
    }
}
