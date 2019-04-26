package models.cards;

public class Hero extends Attacker {

    @Override
    public String toString() {
        return "Name : " + getName() + " - AP : " + getAttackPoint() + " - HP : " +  getHealth() + " - Class : " +
                getAttackType() + " - Special power : " + spcialPower ;
    }

    public Hero(String name, String description, int manaPoint, int price, int health, int attackPoint,
                AttackType attackType, int range) {
        super(name, description, manaPoint, price);
    }
}
