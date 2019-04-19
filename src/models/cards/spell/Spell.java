package models.cards.spell;

import models.cards.Card;
import models.map.Cell;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Card {
    private List<Cell> targetCells = new ArrayList<>();

    private TargetType targetType;

    public TargetType getTargetType() { return targetType; }
    void doEffect() {}

    public Spell(int id, String name, String description, int manaPoint, int price) {
        super(id, name, description, manaPoint, price);
    }
}
