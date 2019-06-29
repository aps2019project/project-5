package client.models.items;

import client.models.map.Cell;

public class CollectableItem extends Item {
    private boolean isCollected;
    private CollectableTypes collectableTypes;
    private String playerName;
    private int id;

    public CollectableItem(String name, Cell cell, String description) {
        super(name, cell, description);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getID() {
        return String.format("%s_%s_%s", playerName, getName(), id);
    }

    public enum CollectableTypes {

    }

    public static class NoCollectableItemSelected extends Exception{
        public NoCollectableItemSelected(String message) {
            super(message);
        }
    }

}
