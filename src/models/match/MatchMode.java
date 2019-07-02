package models.match;

public enum MatchMode {
    DEATH_MATCH(1),
    CAPTURE_THE_FLAG_MATCH(2),
    MULTI_FLAG_MATCH(3);
    int mode;

    public static MatchMode getByInt(int n) {
        switch (n) {
            case 2:
                return CAPTURE_THE_FLAG_MATCH;
            case 3:
                return MULTI_FLAG_MATCH;
            default:
                return DEATH_MATCH;
        }
    }

    MatchMode(int mode) {
        this.mode = mode;
    }
}
