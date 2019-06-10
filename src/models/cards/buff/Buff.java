package models.cards.buff;

import models.cards.Card;

public abstract class Buff {
    int activeTime;
    private int maxActiveTime;
    boolean isContinues;

    public boolean buffIsActivated() {
        if (isContinues) return true;
        return activeTime <= maxActiveTime;
    }

    public Buff(int maxActiveTime, boolean isContinues) {
        this.maxActiveTime = maxActiveTime;
        this.isContinues = isContinues;
    }

    public Buff() {
    }

    public abstract void buffEffect(Card card);

    public static class CardIsNotAvailableException extends Exception {
        public CardIsNotAvailableException() {
            super("Card is not available");
        }
    }
}
