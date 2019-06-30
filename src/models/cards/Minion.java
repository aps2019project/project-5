package models.cards;

public class Minion extends Attacker {
    public Minion(String name, String description, int price, int manaPoint, int health, int attackPoint, AttackType attackType, int attackRange) {
        super(name, description, price, manaPoint, health, attackPoint, attackType, attackRange);
    }
}
