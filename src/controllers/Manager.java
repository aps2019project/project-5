package controllers;

import models.*;
import models.cards.Card;
import models.Collection.CardNotFoundException;
import models.Account.NotEnoughDrakeException;
import models.cards.Minion;
import models.map.Cell;
import models.map.Map;
import models.match.DeathMatch;
import models.match.Match;
import models.match.MultiFlagMatch;
import models.match.SingleFlagMatch;
import views.Error;
import views.Input;
import views.InputAI;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private static Account account;
    private static Match playingMatch;
    private static Shop shop = Shop.getInstance();
    private static ArrayList<Match> matches = new ArrayList<>();

    private static String opponentUsername;

    public static Account getAccount() {
        return account;
    }

    public static void addAccount(Account account) throws Account.UsernameExistsException {
        Account.addAccount(account);
    }

    public static void login(String username, String password) throws Account.InvalidPasswordException, Account.InvalidUsernameException {
        account = Account.getAccount(username, password);
    }

    public static ArrayList<Account> getLeaderboard() {
        return Account.getRanking();
    }

    public static Match getPlayingMatch() {
        return playingMatch;
    }

    public static void createDeck(String name) throws Account.NotLoggedInException, Account.DeckExistsException {
        if (account == null) {
            throw new Account.NotLoggedInException();
        }
        Deck deck = new Deck(name);
        account.addDeck(deck);
    }

    public static void deleteDeck(String name) throws Account.DeckNotFoundException {
        account.deleteDeck(name);
    }

    public static List<Deck> getDecks() throws Account.NotLoggedInException {
        if (account == null)
            throw new Account.NotLoggedInException();
        return account.getDecks();
    }

    public static List<Card> searchCardInShop(String cardName) throws CardNotFoundException {
        return shop.searchCards(cardName);
    }

    public static void buy(String cardName) throws Collection.CollectionException, NotEnoughDrakeException {
        shop.buy(account, cardName);
    }

    public static void sell(String cardName) throws CardNotFoundException {
        shop.sell(account, cardName);
        for (Deck deck : account.getDecks()) {
            deck.getCards().stream()
                    .filter(card -> card.getName().equalsIgnoreCase(cardName))
                    .forEach(card -> deck.getCards().remove(card));

        }
    }

    public static Collection getShopCollection() {
        Collection collection = shop.getCardsCollection();
        return shop.getCardsCollection();
    }


    public static Collection getMyCollection() {
        Collection collection = account.getCollection();
        return collection;
    }

    public static List<Card> searchMyCard(String cardName) throws CardNotFoundException {
        return account.getCollection().getCardsList(cardName);
    }

    public static void addCardToDeck(String cardName, String deckName) throws
            Account.DeckNotFoundException, Deck.HeroExistsInDeckException,
            Deck.HeroNotExistsInDeckException, Deck.DeckFullException, CardNotFoundException {
        Card card = account.getCollection().getCard(cardName);
        card.setUsername(account.getUsername());
        Deck deck = account.getDeck(deckName);
        deck.addCard(card);
    }

    public static void removeCardFromDeck(String cardName, String deckName) throws CardNotFoundException,
            Account.DeckNotFoundException {
        Card card = account.getCollection().getCard(cardName);
        Deck deck = account.getDeck(deckName);
        deck.removeCard(card);
    }

    public static String getMatchInfo() {
        return "Player 1 mana: " + playingMatch.getPlayer1().getMana() +
                "Player 2 mana: " + playingMatch.getPlayer2().getMana() +
                playingMatch.getInfo();
    }

    public static boolean isValid(String deckName) throws Account.DeckNotFoundException {
        Deck deck = account.getDeck(deckName);
        return deck.isValid();
    }

    public static void selectDeck(String deckName) throws Account.DeckNotFoundException {
        Deck deck = account.getDeck(deckName);
        account.setMainDeck(deck);
    }

    public static Input getInput() {
        if (playingMatch == null)
            return Input.getInstance();
        if (playingMatch.isAIMode() && playingMatch.getTurn() % 2 == 1)
            return InputAI.getInstance();
        return Input.getInstance();
    }

    public static String getAIMove() {
        playingMatch.getPlayer2().decide();
        return playingMatch.getPlayer2().getDecision();
    }

    public static Player getActivePlayer() {
        return playingMatch.getActivePlayer();
    }

    public static Player getInActivePlayer() {
        return playingMatch.getInActivePlayer();
    }

    public static void selectCard(String cardID) throws CardNotFoundException {
        playingMatch.getActivePlayer().selectCard(playingMatch.getActivePlayer().getCard(cardID));
    }

    public static boolean canPlay(String username) throws Account.InvalidUsernameException, Account.CantPlayWithYourselfException {
        Account playerAccount = Account.getAccounts().get(username);
        if (playerAccount == null)
            throw new Account.InvalidUsernameException(username);
        if (username.equals(account.getUsername()))
            throw new Account.CantPlayWithYourselfException();
        if (playerAccount.getMainDeck() == null)
            return false;
        return playerAccount.getMainDeck().isValid();
    }

    public static List<Minion> showMyMinions() {
        return playingMatch.showMyMinions();
    }

    public static List<Minion> showOpponentMinions() {
        return playingMatch.showOpponentMinions();
    }

    public static void setMatchData(boolean isAIMode, int gameMode, String username) {
        opponentUsername = username;
        if (!isOpponentNull()) {
            Account opponent;
            if (isAIMode)
                opponent = Account.getAiAccount();
            else
                opponent = Account.getAccounts().get(username);
            if (gameMode == 1 /* story mode */ ) {
                matches.add(new DeathMatch(account, opponent));
                matches.add(new MultiFlagMatch(account, opponent));
                matches.add(new SingleFlagMatch(account, opponent));
                playingMatch = matches.get(0);
                matches.remove(0);
            } else if (gameMode == 2 /* death match */ ) {
                playingMatch = new DeathMatch(account, opponent);
            } else if (gameMode == 3 /* multi flag match */ ) {
                playingMatch = new MultiFlagMatch(account, opponent);
            } else if (gameMode == 4 /* single flag match */ ) {
                playingMatch = new SingleFlagMatch(account, opponent);
            }
            if (opponent == null) {
                opponentUsername = "";
            }
        }
    }

    public static boolean isOpponentNull() {
        if (opponentUsername == null)
            return true;
        return opponentUsername.equals("");
    }

    public static void moveTo(int x, int y) throws Match.InvalidMoveException, Map.InvalidCellException {
        playingMatch.moveTo(x, y);
    }

    public static Card showCardInfo(String name) throws CardNotFoundException {
        return shop.searchCard(name);
    }

    public static void insertCard(String cardID, int x, int y) throws Collection.CollectionException,
            Map.InvalidCellException, Player.NotEnoughManaException, Map.InvalidTargetCellException {
        Card card = getActivePlayer().getHand().getCard(cardID);
        Cell cell = playingMatch.getMap().getCell(x - 1, y - 1);
        if(playingMatch.getActivePlayer().getMana() < card.getManaPoint())
            throw new Player.NotEnoughManaException(Error.NOT_ENOUGH_MANA.toString());
        playingMatch.getMap().insertCard(card, cell);
        getActivePlayer().insertCard(card, cell);
    }

    public static void attack(String ID) throws Match.CardAttackIsNotAvailableException, Match.TiredMinionException,
            CardNotFoundException, Match.OpponentMinionIsNotAvailableForAttack {
        playingMatch.attack(ID);
    }

    public static Hand getHand() {
        return getActivePlayer().getHand();
    }

    public static Map getMap() {
        return playingMatch.getMap();
    }
}
