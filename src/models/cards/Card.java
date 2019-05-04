package models.cards;

import models.cards.spell.Buff;
import models.map.Cell;

public class Card {
    private Cell cell;
    private Buff buff;
    private int manaPoint;
    private int id;
    private String description;
    private String name;
    private int price;
    final int MAX_DISTANCE_TO_MOVE = 2;
    private int nessacaryManaToInsert;

    public Card(Card card) {
        this.cell = card.cell;
        this.buff = card.buff;
        this.manaPoint = card.manaPoint;
        this.id = card.id;
        this.description = card.description;
        this.name = card.name;
        this.price = card.price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void moveCard(Cell cell) {
    }

    public void attack(Cell cell) {
    }

    public Cell getCell() {
        return this.cell;
    }

    public Buff getBuff() {
        return this.buff;
    }

    public int getManaPoint() {
        return this.manaPoint;
    }


    // this constructor can be called only in hero, minion and usable item class
    protected Card(int id, String name, String description, int manaPoint, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manaPoint = manaPoint;
        this.price = price;
    }

    public String showInfo(){
        StringBuilder result=new StringBuilder();
        return result.toString();
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        return object instanceof Card && ((Card) object).getName().equals(this.name);
    }

    public int getMaxDistance() {
        return this.getMaxDistance();

    }

    public int getNessacaryManaToInsert() {
        return this.nessacaryManaToInsert;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
