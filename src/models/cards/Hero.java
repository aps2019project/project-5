package models.cards;

public class Hero extends Attacker {
    private int coolDown;

    @Override
    public String toString() {
        return "Name : " + getName() +
                " - AP : " + getAttackPoint() +
                " - HP : " + getHealth() +
                " - Class : " + getAttackType() +
                " - Special power : " + getSpecialPower();
    }

    public Hero(int id, String name, String description, int manaPoint, int price, int health, int attackPoint,
                AttackType attackType, int range, int coolDown) {
        super(id, name, description, manaPoint, price, health, attackPoint, attackType, range);
        this.coolDown = coolDown;
    }

    public Hero(Card card) {
        super(card);
        this.coolDown = ((Hero)card).coolDown;
    }

    @Override
    public String showInfo() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Hero :\n\tname : %s\n\tcost : %d\n\tdesc : %s"
                , this.getName(), this.getPrice(), this.getDescription()));

        return result.toString();
    }

    public int getCoolDown() {
        return coolDown;
    }
}
