package models;

import models.cards.Card;
import models.items.Flag;
import models.items.Item;
import models.map.Cell;
import views.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private Account account;
    private Deck deck;
    private Hand hand;
    private Card selectedCard;
    private int flags;
    private List<Item> collectedItems = new ArrayList<>();
    private ArrayList<Card> graveYard;
    private Collection activeCards;
    private int mana;
    private Input input;
    private String decision;

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public Player(Account account) throws Collection.CollectionException {
        this.account = account;
        this.deck = new Deck(account.getMainDeck());
        this.shuffleDeck();
        this.setHand();
        this.setCardsId();
        this.setNextCard();
    }

    public ArrayList<Card> getGraveYard() {
        return graveYard;
    }

    public Hand getHand() {
        return hand;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void insertCard(Card card, Cell cell) throws Collection.CollectionException {
        changeMana(-card.getNessacaryManaToInsert());
        activateCard(card);
        card.setCell(cell);
    }

    private void activateCard(Card card) throws Collection.CollectionException {
        activeCards.addCard(card);
    }

    public Deck getDeck() {
        return deck;
    }

    public void incrementFlags() {
        this.flags += flags;
    }

    public void setCollectedItems(Item collectedItems) {
        this.collectedItems.add(collectedItems);
    }


    public List<Flag> getFlags() {
        return activeCards.getFlags();
    }

    public Collection getActiveCards() {
        return activeCards;
    }

    public boolean hasFlag() {
        return getFlags().size() > 0;
    }

    public void setCardsId() {
        int i = 0;
        for (Card card : getDeck().getCards())
            card.setID(++i);
    }

    private void shuffleDeck() {
        Collections.shuffle(deck.getCards());
    }

    private void setHand() throws Collection.CollectionException {
        for (int i = 0; i < 5; i++) {
            hand.addCard(deck.getCards().get(i));
        }
    }

    private void setNextCard() {
        hand.setNextCard(deck.getCards().get(5));
    }



    public void decide() {

    }

    public String getDecision() {
        return decision;
    }

    public void selectCard(Card card) {
        this.selectedCard = card;
    }

    public Card getCard(String cardID) throws Collection.CardNotFoundException {
        return activeCards.getCardByID(cardID);
    }

    public void changeMana(int mana) {
        this.mana += mana;
    }

    public static class NotEnoughManaException extends Exception {
        public NotEnoughManaException(String message) {
            super(message);
        }
    }
}
