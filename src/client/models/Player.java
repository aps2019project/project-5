package client.models;

import client.models.cards.Attacker;
import client.models.cards.Card;
import client.models.cards.Hero;
import client.models.cards.spell.Spell;
import client.models.items.CollectableItem;
import client.models.items.Flag;
import client.models.items.Item;
import client.models.map.Cell;
import client.views.Error;
import client.views.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private Account account;
    private Deck deck;
    private Hand hand = new Hand();
    private Card selectedCard;
    private int flagsNumber;
    private List<Item> collectedItems = new ArrayList<>();
    private ArrayList<Card> graveYard;
    private ArrayList<Attacker> activeCards = new ArrayList<>();
    private int mana = 2;
    private Input input;
    private CollectableItem selectedCollectableItem;

    public Account getAccount() {
        return account;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public Player(Account account) {
        this.account = account;
        this.deck = new Deck(account.getMainDeck());
        this.setCardsId();
        this.shuffleDeck();
        this.setHand();
        this.setNextCard();
    }

    public String getDecision() {
        AI ai = (AI)account;
        return ai.getDecision();
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

    private void setNextHand(Card card) {
        hand.getCards().remove(card);
        hand.getCards().add(hand.getNextCard());
        hand.setNextCard(deck.getCards().get(0));
        deck.getCards().remove(deck.getCards().get(0));
    }

    public void insertCard(Card card, Cell cell) {
        changeMana(-card.getManaPoint());
        setNextHand(card);
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
        this.flagsNumber += flagsNumber;
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
            return activeCards.stream().filter(card -> card.getID().equalsIgnoreCase(cardID)).collect(Collectors.toList()).get(0);
        } catch (Exception e) {
            throw new Collection.CardNotFoundException("Card not found with this ID");
        }
    }

    public void selectCard(Card card) {
        this.selectedCard = card;
    }

    public Card getCard(String cardID) throws Collection.CardNotFoundException {
        for (Card card : activeCards)
            if (card.getID().equalsIgnoreCase(cardID))
                return card;
        throw new Collection.CardNotFoundException(Error.CARD_NOT_FOUND.toString());
    }

    public void changeMana(int mana) {
        this.mana += mana;
    }

    public List<Item> getCollectedItems() {
        return collectedItems;
    }

    public void selectCollectableItem(String itemID) throws ItemNotFoundException {
        List<CollectableItem> collectableItems = collectedItems.stream().filter(
                item -> ((CollectableItem) item).getID().equals(itemID)).map(
                item -> (CollectableItem) item)
                .collect(Collectors.toList());
        if (collectableItems.size() == 0)
            throw new ItemNotFoundException(Error.NO_ITEM.toString());
        this.selectedCollectableItem = collectableItems.get(0);
    }

    public CollectableItem getSelectedCollectableItem() {
        return selectedCollectableItem;
    }

    public boolean collectableItemIsSelected() {
        return selectedCollectableItem != null;
    }

    public boolean hasFlag() {
        return this.flagsNumber > 0;
    }

    public List<Flag> getFlags() {
        return activeCards.stream().map(Attacker::getFlag).collect(Collectors.toList());
    }

    public int getFlagsNumber() {
        return flagsNumber;
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

    public static class NoItemSelectedException extends Exception {
        public NoItemSelectedException(String message) {
            super(message);
        }
    }

    public class ItemNotFoundException extends Throwable {
        public ItemNotFoundException(String message) {
            super(message);
        }
    }

    public static class CardNotSelectedException extends Exception {
        public CardNotSelectedException(String message) {
            super(message);
        }
    }

}
