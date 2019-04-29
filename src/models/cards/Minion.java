package models.cards;
import models.cards.spell.SpecialPowerActivateTime;
import models.items.Flag;

public class Minion extends Attacker {


    public SpecialPowerActivateTime specialPowerActivateTime;

    public Minion(int id, String name, String description, int manaPoint, int price, int health, int attackPoint,
                  AttackType attackType, int range, SpecialPowerActivateTime specialPowerActivateTime) {
        super(id, name, description, manaPoint, price, health, attackPoint, attackType, range);
        this.specialPowerActivateTime = specialPowerActivateTime;
    }



    @Override
    public String toString() {
        String specialPower = "";
        try {
            specialPower = " - Special power : " + getSpecialPower().getDescription();
        } catch (Exception ignored) {}
        return "Type : Minion - Name : " + getName() +
                " - Class : " + getAttackType() +
                " - AP : " + getAttackPoint() +
                " - HP : " + getHealth() +
                " - MP : " + getManaPoint() +
                specialPower;
                /* phrases that should be printed in shop for a special power*/
    }


}
