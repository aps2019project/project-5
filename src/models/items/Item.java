package models.items;

import models.MarketObject;
import models.map.Cell;

public class Item extends MarketObject {
    private Cell cell;
    private String description;
    private String name;


    @Override
    public String getDescription() {
        return this.description;
    }
    
    @Override
    public String toString() {
        return "Name : " + getName() + " - Desc : " + getDescription();
    }
}
