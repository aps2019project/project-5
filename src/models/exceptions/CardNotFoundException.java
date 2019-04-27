package models.exceptions;

public class CardNotFoundException extends Exception{
    public CardNotFoundException (String message) {
        super(message);
    }
}
