package models.items;

import models.map.Cell;

public class CollectableItems extends Item {
    private boolean isCollected;
    private CollectableTypes collectableTypes;

    public CollectableItems(String name, Cell cell, String description) {
        super(name, cell, description);
    }

    public enum CollectableTypes {

    }

}
