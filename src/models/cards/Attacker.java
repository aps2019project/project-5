package models.cards;

import models.cards.buff.Buff;
import models.cards.spell.Spell;
import models.items.Flag;

import java.util.ArrayList;

public class Attacker extends Card {
    private int health;
    private int range;
    private int attackPoint;
    private int currentHealth;
    private AttackType attackType;
    private Spell specialPower;
    private boolean turnAttackAvailability;
    private boolean counterAttackAbility;
    private ArrayList<Buff> buffActivated = new ArrayList<>();

    public ArrayList<Buff> getBuffActivated() {
        return buffActivated;
    }

    public void makeBuffClear() {
        buffActivated = new ArrayList<>();
    }

    public void addBuffActivated(Buff buff) {
        this.buffActivated.add(buff);
    }

    public boolean isCounterAttackAbility() {
        return counterAttackAbility;
    }

    public void setCounterAttackAbility(boolean counterAttackAbility) {
        this.counterAttackAbility = counterAttackAbility;
    }

    Flag flag;

    public void setTurnAttackAvailability(boolean b) {
        this.turnAttackAvailability = b;
    }

    public boolean getTurnAttackAvailability() {
        return this.turnAttackAvailability;
    }


    public Attacker(int id, String name, String description, int manaPoint, int price, int health, int attackPoint,
                    AttackType attackType, int range) {
        super(id, name, description, manaPoint, price);
        this.currentHealth = this.health = health;
        this.attackPoint = attackPoint;
        this.attackType = attackType;
        this.range = range;
    }

    public int getHealth() {
        return health;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void decrementCurrentHealth(int healthPoint) {
        currentHealth -= healthPoint;
    }

    public int getRange() {
        return range;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }

    public boolean hasFlag() {
        return flag != null;
    }

    public Flag getFlag() {
        return flag;
    }

    @Override
    public String showInfo() {
        return super.showInfo();
    }

    public void decrementHP(int attackPoint) {
        this.currentHealth -= attackPoint;
    }

    public void incrementHP(int healthPoint) {
        this.currentHealth += healthPoint;
    }


    public void incrementAP(int attackPoint) {
        this.attackPoint += attackPoint;
    }

    public void decrementAP(int attackPoint) {
        this.attackPoint -= attackPoint;
    }

}
