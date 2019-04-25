package models.items;

import models.Marketable;
import models.map.Cell;

public class Item implements Marketable {
    private Cell cell;
    private String description;
    private String name;
    private int id;
    private int price;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
