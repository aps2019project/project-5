package models;

import models.cards.Card;

import java.util.ArrayList;
import java.util.Deque;

public class Player {
    private Account account;
    private Deque<Card> hand;
    private Card selectedCard;
    private ArrayList<Card> graveYard;
    private Player(Account account){
        
    }
    public ArrayList<Card> getGraveYard() { return graveYard; }
    public Deque<Card> getHand() { return hand; }
    public Card getSelectedCard() { return selectedCard; }
    public void insertCard(Card card) {}
}
