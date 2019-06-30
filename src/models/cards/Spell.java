package models.cards;

public class Spell extends Card {
    public TargetType targetType;

    public Spell(String name, String description, int price, int manaPoint, TargetType targetType) {
        super(name, description, price, manaPoint);
        this.targetType = targetType;
    }

    public Spell() {
        super();
    }
}
