package models.cards.spell;

import models.cards.Attacker;
import models.map.Cell;

public class Buff {
    private BuffType type;

    public BuffType getType() { return type; }

    public enum BuffType {
        HOLY_BUFF,
        POWER_BUFF,
        POISON_BUFF,
        STUN_BUFF,
        WEAKNESS_BUFF,
        DISARM_BUFF
    }

    public void holyBuffEffect(int healthImprove, Cell cell) {
    }

    public void powerEffect() {

    }

    public void poisonEffect() {

    }

    public void stunEffect() {

    }

    public void weaknessEffect() {
    }

    public void disarmEffect() {
    }

}
