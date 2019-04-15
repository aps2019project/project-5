package models.items;

import models.cards.Card;

public class UsableItem extends Card {
    public String getName() { return name; }
    public ItemEffect getItemEffect() { return itemEffect; }

    private String name;
    private ItemEffect itemEffect;
}
