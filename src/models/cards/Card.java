package models.cards;

import models.cards.spell.Buff;
import models.map.Cell;

public class Card implements Comparable {
    private Cell cell;
    private Buff buff;
    private int manaPoint;
    private String cardID;
    private String description;
    private String name;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int price;
    final int MAX_DISTANCE_TO_MOVE = 2;
    private int nessacaryManaToInsert;
    private boolean isMoveAvailable;

    // cardID = playerName +
    public Card(Card card, String cardID) {
        this.cell = card.cell;
        this.buff = card.buff;
        this.manaPoint = card.manaPoint;
        this.description = card.description;
        this.name = card.name;
        this.price = card.price;
        this.cardID = cardID;
    }

    public String getDescription() {
        return description;
    }

    public void moveCard(Cell cell) {
    }

    public void attack(Cell cell) {
    }

    public String getCardID() {
        return this.cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Buff getBuff() {
        return this.buff;
    }

    public int getManaPoint() {
        return this.manaPoint;
    }


    // this constructor can be called only in hero, minion and usable item class
    protected Card(String cardID, String name, String description, int manaPoint, int price) {
        this.cardID = cardID;
        this.name = name;
        this.description = description;
        this.manaPoint = manaPoint;
        this.price = price;
    }

    public String showInfo() {
        StringBuilder result = new StringBuilder();
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

    public boolean isMoveAvailable() {
        return isMoveAvailable;
    }

    public void setMoveAvailable(boolean moveAvailable) {
        isMoveAvailable = moveAvailable;
    }

    @Override
    public int compareTo(Object o) {
        return this.cardID.compareTo(((Card) o).getName());
    }

    public String getID() {
        return cardID;
    }
}

