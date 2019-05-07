package models.cards;

import models.cards.buff.Buff;
import models.cards.spell.Spell;
import models.items.UsableItem;
import models.map.Cell;

public class Card implements Comparable {
    private Cell cell;
    private Buff buff;
    private int manaPoint;
    private String username;
    private int id;
    private String description;
    private String name;
    private int price;
    final int MAX_DISTANCE_TO_MOVE = 2;
    private boolean isMoveAvailable;

    public Card(Card card) {
        this.cell = card.cell;
        this.buff = card.buff;
        this.manaPoint = card.manaPoint;
        this.description = card.description;
        this.name = card.name;
        this.price = card.price;
    }

    public static Card getInstanceOf(Card card) {
        if (card instanceof Hero)
            return new Hero(card);
        if (card instanceof Minion)
            return new Minion(card);
        if (card instanceof UsableItem)
            return new UsableItem(card);
        if (card instanceof Spell)
            return new Spell(card);
        return null;
    }

    public String getDescription() {
        return description;
    }

    public void moveCard(Cell cell) {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void attack(Cell cell) {
    }

    public String getID() {
        return String.format("%s_%s_", username, name) + id;
    }

    public int getId() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
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
    protected Card(int id, String name, String description, int manaPoint, int price) {
        this.id = id;
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
        return this.MAX_DISTANCE_TO_MOVE;

    }

    public boolean isMoveAvailable() {
        return isMoveAvailable;
    }

    public void setMoveAvailable(boolean moveAvailable) {
        isMoveAvailable = moveAvailable;
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((Card) o).getName());
    }

    public String getUsername() {
        return username;
    }
}

