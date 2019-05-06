package models.cards.buff;

import models.cards.Card;

public abstract class Buff {
    int activeTime;
    private int maxActiveTime;
    boolean isContinues;
    BuffType buffType;

    public enum BuffType {
        DISARM_BUFF,
        HOLY_BUFF,
        POISON_BUFF,
        POWER_BUFF,
        STUN_BUFF,
        WEAKNESS_BUFF,
        OPPONENT_BUFF_KILLER,
        MY_BUFF_KILLER;
    }

    public BuffType getBuffType() {
        return buffType;
    }

    public void setBuffType(BuffType buffType) {
        this.buffType = buffType;
    }

    public Buff(int maxActiveTime, boolean isContinous) {
        this.maxActiveTime = maxActiveTime;
        this.isContinues = isContinous;
    }

    public Buff() {}

    public abstract void buffEffect(Card card);

    public static class CardIsNotAvailableException extends Exception {
        public CardIsNotAvailableException() {
            super("Card is not available");
        }
    }
}
