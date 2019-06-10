package models.cards;

public enum AttackType {
    RANGED("Ranged"),
    MELEE ( "Melee"),
    HYBRID ("Hybrid");

    String name;

    AttackType(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
