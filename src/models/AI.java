package models;

import controllers.Manager;
import models.map.Cell;
import models.map.Map;
import models.match.Match;
import views.Output;

import java.util.*;

public class AI extends Account {

    Match match;
    private Queue<String> decisions = new LinkedList<>();
    /// state[distanceToHero][oppHealth][myHealth][action]
    int[][][][] state = new int[10][10][10][3];
    private final int ACTIONS_NUMBER = 3;
    int[] lastState = new int[3];

    public AI(String username, String password) {
        super(username, password);
    }

    private int distanceToOppHero() {
        int distance = 0;
        try {
            distance = Cell.manhattanDistance(match.getPlayer1().getHero().getCell(),
                    match.getPlayer2().getHero().getCell()) - 1;
            if (distance >= 9)
                distance = 9;
        } catch (Exception ignored) {
            Output.err("Ignored in AI");
        }
        return distance;
    }

    private int getHealth() {
        int health = 0;
        try {
            health = match.getPlayer2().getHero().getCurrentHealth() / 5;
            if (health >= 9)
                health = 9;
        } catch (Exception ignored) {
            Output.err("Ignored in AI");
        }
        return health;
    }

    private int getOppHealth() {
        int health = 0;
        try {
            health = match.getPlayer1().getHero().getCurrentHealth() / 5;
            if (health >= 9)
                health = 9;
        } catch (Exception ignored) {
            Output.err("Ignored in AI");
        }
        return health;
    }

    int t = 0;

    public void decide() {
        reward();
        int distance = lastState[0] = distanceToOppHero();
        int myHealth = lastState[1] = getHealth();
        int oppHealth = lastState[2] = getOppHealth();
        int max = 0;
        for (int i = 0; i < ACTIONS_NUMBER; i++) {
            max = Math.max(max, state[distance][oppHealth][myHealth][i]);
        }
        if (max < 100) {
            chooseAction(new Random().nextInt() % 3);
            return;
        }
        for (int i = 0; i < ACTIONS_NUMBER; i++) {
            if (state[distance][oppHealth][myHealth][i] == max) {
                chooseAction(i);
                return;
            }
        }
//        if (t % 2 == 0)
//            this.decision = "show hand";
//        else
//            this.decision = "end turn";
//        t = (t + 1) % 2;
    }

    private void reward() {

    }

    private void chooseAction(int action) {
        switch (action) {
            case 0:
                insertCard();
                break;
            case 1:
                moveCard();
                break;
            case 2:
                attack();
                break;
            default:
                decisions.add("end turn");
        }
    }

    private void attack() {
        selectCard();
        try {
            decisions.add("attack " + match.getPlayer1().getHero().getID());
        } catch (Player.HeroDeadException e) {
            Output.err("ignored in attack in AI");
        }
    }

    private void insertCard() {
        decisions.add("insert " + match.getPlayer2().getHand().getCards().get(Match.random(0, 4)).getID() + " in " +
                Match.random(1, Map.ROW_NUMBER) + ", " + Match.random(1, Map.COLUMN_NUMBER));
    }

    private void selectCard() {
        try {
            decisions.add("select " + match.getPlayer2().getHero().getID());
        } catch (Player.HeroDeadException e) {
            Output.err("ignored in insertCard in AI");
        }
    }

    private void moveCard() {
        selectCard();
        decisions.add("move to " + Match.random(1, Map.ROW_NUMBER) + ", " + Match.random(1, Map.COLUMN_NUMBER));
    }


    public String getDecision() {
        if (match.getPlayer2().getMana() < 2)
            return "end turn";
        if (decisions.size() == 0)
            decide();
        return decisions.poll();
    }

    public void setMatch(Match playingMatch) {
        this.match = playingMatch;
    }

    public static AI getAIAccount() {
        AI aiAccount = new AI("AI", "password");
        try {
            Shop.getInstance().buy(aiAccount, "rostam");
            Shop.getInstance().buy(aiAccount, "fire dragon");
            Shop.getInstance().buy(aiAccount, "eagle");
            Shop.getInstance().buy(aiAccount, "Hog Head Demon");
            Shop.getInstance().buy(aiAccount, "Persian Swordsman");
            Shop.getInstance().buy(aiAccount, "Persian Horse Rider");
            Shop.getInstance().buy(aiAccount, "Persian Horse Rider");
            Shop.getInstance().buy(aiAccount, "Persian Horse Rider");
            Shop.getInstance().buy(aiAccount, "Persian Horse Rider");
            Shop.getInstance().buy(aiAccount, "Persian Champion");
            Shop.getInstance().buy(aiAccount, "Persian Champion");
            Shop.getInstance().buy(aiAccount, "Turan Archer");
            Shop.getInstance().buy(aiAccount, "Turan Archer");
            Shop.getInstance().buy(aiAccount, "Turan Wand");
            Shop.getInstance().buy(aiAccount, "Turan Wand");
            Shop.getInstance().buy(aiAccount, "persian horse rider");
            Shop.getInstance().buy(aiAccount, "persian horse rider");
            Shop.getInstance().buy(aiAccount, "persian horse rider");
            Shop.getInstance().buy(aiAccount, "persian horse rider");
            Shop.getInstance().buy(aiAccount, "persian horse rider");
            Shop.getInstance().buy(aiAccount, "persian horse rider");
            aiAccount.addDeck(new Deck("AIDeck"));
            Deck deck = aiAccount.getDeck("AIDeck");
            Manager.addCardToDeck(aiAccount, deck, "rostam");
            Manager.addCardToDeck(aiAccount, deck, "fire dragon");
            Manager.addCardToDeck(aiAccount, deck, "eagle");
            Manager.addCardToDeck(aiAccount, deck, "Hog Head Demon");
            Manager.addCardToDeck(aiAccount, deck, "Persian Swordsman");
            Manager.addCardToDeck(aiAccount, deck, "Persian Horse Rider");
            Manager.addCardToDeck(aiAccount, deck, "Persian Horse Rider");
            Manager.addCardToDeck(aiAccount, deck, "Persian Horse Rider");
            Manager.addCardToDeck(aiAccount, deck, "Persian Horse Rider");
            Manager.addCardToDeck(aiAccount, deck, "Persian Champion");
            Manager.addCardToDeck(aiAccount, deck, "Persian Champion");
            Manager.addCardToDeck(aiAccount, deck, "Turan Archer");
            Manager.addCardToDeck(aiAccount, deck, "Turan Archer");
            Manager.addCardToDeck(aiAccount, deck, "Turan Wand");
            Manager.addCardToDeck(aiAccount, deck, "Turan Wand");
            Manager.addCardToDeck(aiAccount, deck, "persian horse rider");
            Manager.addCardToDeck(aiAccount, deck, "persian horse rider");
            Manager.addCardToDeck(aiAccount, deck, "persian horse rider");
            Manager.addCardToDeck(aiAccount, deck, "persian horse rider");
            Manager.addCardToDeck(aiAccount, deck, "persian horse rider");
            aiAccount.setMainDeck(deck);
        } catch (Exception e) {
            Output.err(e);
        }
        return aiAccount;
    }
}
