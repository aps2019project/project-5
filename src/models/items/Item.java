package models.items;

import models.map.Cell;

public class Item {
    private Cell cell;
    private String description;
    private String name;

    public String getDescription() {
        return this.description;
    }
    
    @Override
    public String toString() {
        return "Name : " + getName() + " - Desc : " + getDescription();
    }

    public String getName() {
        return name;
    }

    public Cell getCell() {
        return cell;
    }
}
