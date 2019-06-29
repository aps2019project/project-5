package client.controllers;

import client.models.*;
import client.models.Account.NotEnoughDrakeException;
import client.models.Collection.CardNotFoundException;
import client.models.cards.Card;
import client.models.cards.Hero;
import client.models.cards.Minion;
import client.models.cards.spell.Spell;
import client.models.items.CollectableItem;
import client.models.items.Item;
import client.models.items.UsableItem;
import client.models.map.Cell;
import client.models.map.Map;
import client.models.match.DeathMatch;
import client.models.match.Match;
import client.models.match.MultiFlagMatch;
import client.models.match.SingleFlagMatch;
import client.views.Error;
import client.views.Input;
import client.views.InputAI;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static client.controllers.Manager.getInActivePlayer;

public class ClientManager {
    private static Account account;
    private static Match playingMatch;
    private static Shop shop = Shop.getInstance();
    private static ArrayList<Match> matches = new ArrayList<>();
    private static Account winnerPlayer = null;
    private static String opponentUsername;
    private static Account opponent;
    private static boolean isAIMode;

    public static Deck getMainDeck() {
        return account.getMainDeck();
    }


    public static void addDeck(Deck deck) throws Account.DeckExistsException {
        account.addDeck(deck);
    }

    public static enum GameMode {
        STORY_MODE, DEATH_MATCH, SINGLE_FLAG, MULTI_FLAG;

        public static GameMode getGame(int num) {
            switch (num) {
                case 1:
                    return STORY_MODE;
                case 2:
                    return DEATH_MATCH;
                case 3:
                    return SINGLE_FLAG;
                case 4:
                    return MULTI_FLAG;
            }
            return null;

        }
    }

    public static Account getAccount() {
        return account;
    }

    public static void createAccount(String username, String password) throws Account.UsernameExistsException {
        Account.addAccount(new Account(username, password));
    }

    public static void login(String username, String password) throws Account.InvalidPasswordException, Account.InvalidUsernameException {
        account = Account.getAccount(username, password);

    }

    public static Player getMe() {
        if (playingMatch.getPlayer1().getAccount().getUsername().equals(account.getUsername()))
            return playingMatch.getPlayer1();
        return playingMatch.getPlayer2();
    }

    public static Player getOpponent() {
        if (playingMatch.getPlayer1().getAccount().getUsername().equals(account.getUsername()))
            return playingMatch.getPlayer2();
        return playingMatch.getPlayer1();
    }

    public static Account getWinner() {
        return winnerPlayer;
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

    public static Deck getDeck(String deckName) throws Account.DeckNotFoundException {
        return getAccount().getDeck(deckName);
    }

    public static int getNumberOfCard(Card card) {
        return account.getCollection().getNumberOfCard(card);
    }

    public static void sell(String cardName) throws CardNotFoundException {
        shop.sell(account, cardName);
        // TODO: remove selling cards from decks
    }

    public static Collection getShopCollection() {
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
        Deck deck = account.getDeck(deckName);
        addCardToDeck(account, deck, cardName);
    }

    public static void addCardToDeck(Account account, Deck deck, String cardName) throws CardNotFoundException,
            Deck.HeroExistsInDeckException, Deck.HeroNotExistsInDeckException, Deck.DeckFullException {
        Card newCard = Card.getInstanceOf(account.getCollection().getCard(cardName));
        newCard.setUsername(account.getUsername());
        if (deck.countNumberOf(newCard) == account.getCollection().getNumberOf(account.getCard(cardName)))
            throw new CardNotFoundException();
        deck.addCard(newCard);
    }

    public static void removeCardFromDeck(String cardName, String deckName) throws CardNotFoundException,
            Account.DeckNotFoundException {
//        Card card = account.getCollection().getCard(cardName);
        Deck deck = account.getDeck(deckName);
        deck.removeCard(cardName);
    }

    public static String getMatchInfo() {
        return "Player 1 mana: " + playingMatch.getPlayer1().getMana() + "\t ||| \t" +
                "Player 2 mana: " + playingMatch.getPlayer2().getMana() + "\n" +
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
        return playingMatch.getPlayer2().getDecision();
    }

    public static Player getActivePlayer() {
        return playingMatch.getActivePlayer();
    }

    public static Player getInAcjtivePlayer() {
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
        return playingMatch.showMinions(getActivePlayer());
    }

    public static List<Minion> showOpponentMinions() {
        return playingMatch.showMinions(getInActivePlayer());
    }

    public static void setOpponent(String opponentUsername, boolean isAIMode) throws AccountNotFoundException {
        if (isAIMode) {
            opponent = AI.getAIAccount();
        } else {
            opponent = Account.getAccount(opponentUsername);
            ClientManager.opponentUsername = opponentUsername;
        }
        ClientManager.isAIMode = isAIMode;
    }

    public static void setGameMode(GameMode gameMode) {
        switch (gameMode) {
            case STORY_MODE:
                matches.add(new DeathMatch(account, opponent));
                matches.add(new MultiFlagMatch(account, opponent));
                matches.add(new SingleFlagMatch(account, opponent));
                playingMatch = matches.get(0);
                matches.remove(0);
                break;
            case DEATH_MATCH:
                playingMatch = new DeathMatch(account, opponent);
                break;
            case MULTI_FLAG:
                playingMatch = new MultiFlagMatch(account, opponent);
                break;
            case SINGLE_FLAG:
                playingMatch = new SingleFlagMatch(account, opponent);
                break;
        }
        if (isAIMode)
            ((AI) opponent).setMatch(playingMatch);
        playingMatch.setAIMode(isAIMode);
    }

    public static void setMatchData(boolean isAIMode, GameMode gameMode, String username) {
        opponentUsername = username;
        if (!isOpponentNull()) {
            Account opponent;
            if (isAIMode) {
                opponent = AI.getAIAccount();
            } else
                opponent = Account.getAccounts().get(username);
            if (gameMode == GameMode.STORY_MODE /* story mode */) {
                matches.add(new DeathMatch(account, opponent));
                matches.add(new MultiFlagMatch(account, opponent));
                matches.add(new SingleFlagMatch(account, opponent));
                playingMatch = matches.get(0);
                matches.remove(0);
            } else if (gameMode == GameMode.DEATH_MATCH/* death match */) {
                playingMatch = new DeathMatch(account, opponent);
            } else if (gameMode == GameMode.MULTI_FLAG/* multi flag match */) {
                playingMatch = new MultiFlagMatch(account, opponent);
            } else if (gameMode == GameMode.SINGLE_FLAG/* single flag match */) {
                playingMatch = new SingleFlagMatch(account, opponent);
            }
            if (opponent == null) {
                opponentUsername = "";
            }
            playingMatch.setAIMode(isAIMode);
            if (isAIMode) {
                ((AI) opponent).setMatch(playingMatch);
            }
        }
    }


    public static boolean isOpponentNull() {
        if (opponentUsername == null)
            return true;
        return opponentUsername.equals("");
    }

    public static void moveTo(int x, int y) throws Match.InvalidMoveException, Map.InvalidCellException {
        playingMatch.moveTo(x - 1, y - 1);
    }

    public static Card showCardInfo(String name) throws CardNotFoundException {
        return shop.searchCard(name);
    }

    public static void insertCard(String cardID, int x, int y) throws Map.InvalidCellException,
            Player.NotEnoughManaException, Map.InvalidTargetCellException,
            Player.HeroDeadException, CardNotFoundException {
        Card card = getActivePlayer().getHand().getCard(cardID);
        Cell cell = playingMatch.getMap().getCell(x - 1, y - 1);
        if (playingMatch.getActivePlayer().getMana() < card.getManaPoint())
            throw new Player.NotEnoughManaException(Error.NOT_ENOUGH_MANA.toString());
        playingMatch.getMap().insertCard(card, cell);
        getActivePlayer().insertCard(card, cell);
    }

    public static void attack(String ID) throws Match.CardAttackIsNotAvailableException, Match.TiredMinionException,
            CardNotFoundException, Match.OpponentMinionIsNotAvailableForAttack, Player.CardNotSelectedException {
        playingMatch.attack(ID);
        if (playingMatch.isFinished()) {
            Account winner = playingMatch.getWinner().getAccount();
            winnerPlayer = winner;
            Account loser = playingMatch.getLoser().getAccount();
            winner.getMatchHistory().add(new MatchResult(loser, true, new Date()));
            loser.getMatchHistory().add(new MatchResult(winner, false, new Date()));
            winner.addWinCount();
            playingMatch = null;
        }
    }

    public static Hand getHand() {
        return getActivePlayer().getHand();
    }

    public static Map getMap() {
        return playingMatch.getMap();
    }

    public static void endTurn() {

        playingMatch.nextTurn();
    }

    public static List<Item> getCollectableItems() {
        return getActivePlayer().getCollectedItems();
    }

    public static void selectCollectableItem(String itemID) throws Player.ItemNotFoundException {
        getActivePlayer().selectCollectableItem(itemID);
    }

    public static CollectableItem getSelectedCollectableItem() throws Player.NoItemSelectedException {
        CollectableItem collectableItem = getActivePlayer().getSelectedCollectableItem();
        if (collectableItem == null)
            throw new Player.NoItemSelectedException(Error.NO_SELECTABLE_ITEM_SELECTED.toString());
        return collectableItem;
    }

    public static void useCollectableItem(int x, int y) throws CollectableItem.NoCollectableItemSelected {
        if (!getActivePlayer().collectableItemIsSelected()) {
            throw new CollectableItem.NoCollectableItemSelected(Error.NO_SELECTABLE_ITEM_SELECTED.toString());
        }
        // TODO: 5/7/19 use item in cell(x, y)
    }

    public static Card getNextCard() {
        return getActivePlayer().getHand().getNextCard();
    }

    public static Card getCardInGraveyard(String cardID) throws CardNotFoundException {
        Card card = playingMatch.getGraveyardCard(cardID);
        if (card == null) {
            throw new CardNotFoundException(Error.CARD_NOT_FOUND_IN_GRAVEYARD.toString());
        }
        return card;
    }

    public static List<Card> getCardsInGraveyard() {
        return playingMatch.getGraveyardCards();
    }

    public static boolean isAITurn() {
        return playingMatch.isAIMode() && (playingMatch.getTurn() & 1) == 1;
    }

    public static List<Hero> getHero(List<Card> cards) {
        return cards.stream().filter(
                card -> card instanceof Hero).map(
                card -> (Hero) card)
                .collect(Collectors.toList());
    }

    public static List<Minion> getMinion(List<Card> cards) {
        return cards.stream().filter(
                card -> card instanceof Minion).map(
                card -> (Minion) card)
                .collect(Collectors.toList());
    }

    public static List<Spell> getSpell(List<Card> cards) {
        return cards.stream().filter(
                card -> card instanceof Spell).map(
                card -> (Spell) card)
                .collect(Collectors.toList());
    }

    public static List<UsableItem> getItem(List<Card> cards) {
        return cards.stream().filter(
                card -> card instanceof UsableItem
        ).map(
                card -> (UsableItem) card)
                .collect(Collectors.toList());
    }

    public static void setMapFlags(int result) {
        playingMatch.setFlagsInMap(result);
    }

    public static boolean accountExists(String username) {
        for (String usernameIt : Account.getAccounts().keySet()) {
            if (username.equals(usernameIt))
                return true;
        }
        return false;
    }

    public static boolean hasCard(String cardId) throws CardNotFoundException {
        return account.getCollection().hasCard(account.getCollection().getCardByID(cardId));
    }

    public static boolean isMainDeckSelected() {
        return Deck.isValid(account.getMainDeck());
    }

    public static List<Cell> whereToPut(Card card) {
        List<Cell> cells = new ArrayList<>();
        if (card instanceof Spell)
            return getMap().getCells();
        getMe().getActiveCards().forEach(
                cardIt -> cells.addAll(getMap().getNeighbors(cardIt.getCell())
                        .stream()
                        .filter(
                                cell -> !cell.isFull()
                        ).collect(Collectors.toList())));
        return cells;
    }

    public static List<Cell> whereToMove(Card card) {
        List<Cell> cells = new ArrayList<>();
        if (!card.isMoveAvailable())
            return cells;
        cells.addAll(getMap().getCellsDistance(card.getCell(), 2).stream()
                .filter(cell -> getMap().isValidMove(card, getMe(), cell))
                .collect(Collectors.toList()));
        return cells;
    }
}
