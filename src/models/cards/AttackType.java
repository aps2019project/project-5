package models.cards;

public enum AttackType {
    RANGED("Ranged"),
    MELEE("Melee"),
    HYBRID("Hybrid");

    String name;

    AttackType(String name) {
        this.name = name;
    }

    public static AttackType getAttackType(String type) {
        switch (type) {
            case "RANGED":
                return RANGED;

            case "MELEE":
                return MELEE;

            case "HYBRID":
                return HYBRID;

        }
        return null;
    }


    @Override
    public String toString() {
        return name;
    }
}
