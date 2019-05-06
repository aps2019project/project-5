package models;

import controllers.Manager;
import models.cards.Attacker;
import models.cards.Card;
import models.cards.Hero;
import models.cards.spell.Spell;
import models.items.Flag;
import models.items.Item;
import models.map.Cell;
import models.map.Map;
import views.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private Account account;
    private Deck deck;
    private Hand hand = new Hand();
    private Card selectedCard;
    private int flags;
    private List<Item> collectedItems = new ArrayList<>();
    private ArrayList<Card> graveYard;
    private ArrayList<Attacker> activeCards = new ArrayList<>();
    private int mana;
    private Input input;
    private String decision;

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public Player(Account account) {
        this.account = account;
        this.deck = new Deck(account.getMainDeck());
        this.shuffleDeck();
        this.setCardsId();
        this.setHand();
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
        changeMana(-card.getManaPoint());
        if (card instanceof Attacker) {
            activateCard((Attacker) card);
            card.setCell(cell);
        }
        if (card instanceof Spell) {

        }
    }

    private void activateCard(Attacker attacker) {
        activeCards.add(attacker);
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

    public ArrayList<Attacker> getActiveCards() {
        return activeCards;
    }

    public void setCardsId() {
        int i = 0;
        for (Card card : getDeck().getCards())
            card.setID(++i);
    }

    private void shuffleDeck() {
        Hero hero = deck.getHero();
        deck.getCards().remove(hero);
        Collections.shuffle(deck.getCards());
        try {
            deck.addCard(hero);
        } catch (Exception ignored) {
        }
    }

    public Hero getHero() throws HeroDeadException {
        for (Attacker attacker : activeCards)
            if (attacker instanceof Hero)
                return (Hero) attacker;
        throw new HeroDeadException("Hero doesn't exists in active cards");
    }

    private void setHand() {
        hand = new Hand();
        for (int i = 0; i < 5; i++) {
            Card card = deck.getCards().get(0);
            hand.getCards().add(card);
            deck.getCards().remove(card);
        }
    }

    private void setNextCard() {
        hand.setNextCard(deck.getCards().get(5));
    }

    public Attacker getActiveCard(String cardID) throws Collection.CardNotFoundException {
        try {
            return activeCards.stream().filter(card -> card.getID().equals(cardID)).collect(Collectors.toList()).get(0);
        } catch (Exception e) {
            throw new Collection.CardNotFoundException("Card not found with this ID");
        }
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
        for (Card card : activeCards)
            if (card.getID().equals(cardID))
                return card;
        throw new Collection.CardNotFoundException("Card with this id not found");
    }

    public void changeMana(int mana) {
        this.mana += mana;
    }

    public static class NotEnoughManaException extends Exception {
        public NotEnoughManaException(String message) {
            super(message);
        }
    }

    public static class HeroDeadException extends Exception {
        public HeroDeadException(String message) {
            super(message);
        }
    }
}
