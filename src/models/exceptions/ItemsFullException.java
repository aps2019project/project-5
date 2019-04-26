package models.exceptions;

public class ItemsFullException extends Throwable {
    public ItemsFullException(String message) {
        super(message);
    }
}
