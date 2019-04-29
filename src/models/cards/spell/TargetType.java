package models.cards.spell;

public enum TargetType {
    AN_OPPONENT_FORCE ("attack to one enemy minion."),
    SQUARE2x2 ("attack to a 2×2 square in map."),
    ALL_OPPONENT_HEROES ("attack to all enemy minion."),
    MY_FORCE ("apply to my force."),
    SQUARE3x3 ("attack to a 3×3 square in map."),
    FORCE ("apply to all attackers."),
    ALL_OPPONENT_FORCES_IN_ONE_COLUMN ("attack to all opponent attacker in one column.");

    private final String description;

    TargetType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
