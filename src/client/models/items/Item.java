package client.models.items;

import client.models.map.Cell;

public class Item {
    private Cell cell;
    private String description;
    private String name;

    public Item(String name, Cell cell, String description) {
        this.name = name;
        this.cell = cell;
        this.description = description;
    }

    public Item() {}

    public String getDescription() {
        return this.description;
    }
    
    @Override
    public String toString() {
        return "Name : " + getName() +
                " - Desc : " + getDescription();
    }

    public String getName() {
        return name;
    }

    public Cell getCell() {
        return cell;
    }
}
