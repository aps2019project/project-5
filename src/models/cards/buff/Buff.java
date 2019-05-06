package models.cards.buff;

import models.cards.Card;
import models.map.Cell;
import models.match.Match;

public abstract class Buff {

    int activeTime;
    private int maxActiveTime;

    public Buff(int maxActiveTime) {
        this.maxActiveTime = maxActiveTime;
    }

    public abstract void buffEffect(Card card);

    public static class CardIsNotAvailableException extends Exception {
        public CardIsNotAvailableException() {
            super("Card is not available");
        }
    }
}
