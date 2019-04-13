package models;

import models.cards.Card;
import java.util.ArrayList;

public class Collection {
    private static ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();

    public ArrayList<Card> getCards() { return cards; }

}
