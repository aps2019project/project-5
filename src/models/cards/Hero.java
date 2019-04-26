package models.cards;

public class Hero extends Attacker {

    public Hero(String name, String description, int manaPoint, int price, int health, int attackPoint, AttackType attackType, int range) {
        super(name, description, manaPoint, price, health, attackPoint, attackType, range);
    }
}
