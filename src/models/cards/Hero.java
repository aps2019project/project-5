package models.cards;

public class Hero extends Attacker {



    @Override
    public String toString() {
        return "Name : " + getName() +
                " - AP : " + getAttackPoint() +
                " - HP : " +  getHealth() +
                " - Class : " + getAttackType() +
                " - Special power : " + getSpecialPower();
    }

    public Hero(int id, String name, String description, int manaPoint, int price, int health, int attackPoint,
                AttackType attackType, int range) {
        super(id, name, description, manaPoint, price, health, attackPoint, attackType, range);
    }
}
