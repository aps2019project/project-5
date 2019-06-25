package models;

public enum  Action {
    DEATH("death"),
    ATTACK("attack"),
    BREATHING("breathing"),
    HIT("hit"),
    IDLE("idle"),
    RUN("run"),
    SPELL_IDLE("shieldofphalanx"),
    TELEPORT("teleportrecall");

    String code;

    Action(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
