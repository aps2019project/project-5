package models.cards;

public class Hero extends Attacker {
    public Hero(String name, String description, int price, int manaPoint, int health, int attackPoint, AttackType attackType) {
        super(name, description, price, manaPoint, health, attackPoint, attackType);
    }
}
