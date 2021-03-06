package client.models.cards;

import client.models.cards.buff.Buff;
import client.models.cards.spell.SpecialPowerActivateTime;

import java.util.ArrayList;

public class Minion extends Attacker {
    int comboAbility;

    private ArrayList<Buff> buffs = new ArrayList<>();

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void addBuff(Buff buff) {
        this.buffs.add(buff);
    }

    public void buffGarbage() {
        buffs.forEach(buff -> {
            if (!buff.buffIsActivated()) buffs.remove(buff);
        });

    }

    public SpecialPowerActivateTime specialPowerActivateTime;

    public Minion(int id, String name, String description, int manaPoint, int price, int health, int attackPoint,
                  AttackType attackType, int range, SpecialPowerActivateTime specialPowerActivateTime) {
        super(id, name, description, manaPoint, price, health, attackPoint, attackType, range);
        this.specialPowerActivateTime = specialPowerActivateTime;
    }

    public Minion(Card card) {
        super(card);
        this.specialPowerActivateTime = ((Minion) card).specialPowerActivateTime;
    }


    @Override
    public String toString() {
        String specialPower = "";
        try {
            specialPower = " - Special power : " + getSpecialPower().getDescription();
        } catch (Exception ignored) {
        }
        return "Type : Minion - Name : " + getName() +
                " - Class : " + getAttackType() +
                " - AP : " + getAttackPoint() +
                " - HP : " + getHealth() +
                " - MP : " + getManaPoint() +
                specialPower;
        /* phrases that should be printed in shop for a special power*/
    }

    @Override
    public String showInfo() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Minion :\nName : %s\nHP : %d\tAP : %d\tMP : %d\nRange : %d\nCombo-Ability : %d\ncost : %d\nDesc : %s",
                this.getName(), this.getHealth(), this.getAttackPoint(), this.getManaPoint(), this.getRange(), comboAbility, this.getPrice(), this.getDescription()));
        return result.toString();
    }
}
