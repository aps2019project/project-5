package models;

public enum  Action {
    DEATH("death"),
    ATTACK("attack"),
    BREATHING("breathing"),
    HIT("hit"),
    IDLE("idle"),
    RUN("run");

    String code;

    Action(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
