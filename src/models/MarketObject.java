package models;

public abstract class MarketObject {

    protected int price ;
    protected int id ;
    protected String name;
    protected String description;

    protected String getName() {
        return name;
    }

    protected int getPrice() {
        return price;
    }

    protected int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
