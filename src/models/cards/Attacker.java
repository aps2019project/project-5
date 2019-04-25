package models.cards;

import models.cards.spell.Spell;

public class Attacker extends Card{
    int health ;
    int attackPoint ;
    AttackType attackType ;
    Spell specialPower ;

    public Attacker(String name,String description,int manaPoint,int price) {
        super(name, description, manaPoint, price);
    }
}
