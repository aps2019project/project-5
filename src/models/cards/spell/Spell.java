package models.cards.spell;

import models.cards.Card;
import models.map.Cell;
import java.util.ArrayList;
import java.util.List;

public class Spell extends Card {
    private List<Cell> targetCells = new ArrayList<>();
    private TargetType targetType;
    void doEffect(){

    }

}
