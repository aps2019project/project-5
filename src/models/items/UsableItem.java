package models.items;

import models.cards.Card;

public class UsableItem extends Card {

    private ItemEffect itemEffect;

    public ItemEffect getItemEffect() {
        return itemEffect;
    }

    public UsableItem(String name, String description, int manaPoint, int price, ItemEffect itemEffect) {
        super(name, description, manaPoint, price);
        this.itemEffect = itemEffect;
    }
}
