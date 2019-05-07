package models.cards.spell;

import controllers.Manager;
import models.Player;
import models.cards.Card;
import models.cards.buff.Buff;
import models.map.Cell;
import models.map.Map;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Card {
    private List<Cell> targetCells = new ArrayList<>();

    private TargetType targetType;

    private ArrayList<Buff> buffs = new ArrayList<>();


    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public Spell(int id, String name, String description, int manaPoint, int price) {
        super(id, name, description, manaPoint, price);
    }

    public Spell(Card card) {
        super(card);
    }


    @Override
    public String toString() {
        return "Type : Spell - Name : " + getName() + " - MP : " + getManaPoint() + " - Desc : " + getDescription();
    }

    public String showInfo() {
        StringBuilder result = new StringBuilder();
        result.append(String.format
                ("Spell :\nname : %s\nMP : %d\ncost : %d\ndesc : %s"
                        , this.getName(), this.getManaPoint(), this.getPrice(), this.getDescription()));
        return result.toString();
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }
}
