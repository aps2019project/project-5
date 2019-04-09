package models.items;

import models.cards.Card;

public class UsableItem extends Card {
    public String getName() { return name; }
    public Effect getEffect() { return effect; }

    private String name;
    private Effect effect;

    public static enum Effect {}
}
