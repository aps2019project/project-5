package models;

import controllers.Manager;
import models.match.Match;
import views.Output;

public class AI extends Account {

    Match match;
    private String decision;
    int[][][][] state = new int[10][10][10][3];

    public AI(String username, String password) {
        super(username, password);
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

    int t = 0;
    public void decide() {
        if (t % 2 == 0)
            this.decision = "show hand";
        else
            this.decision = "end turn";
        t = (t + 1) % 2;
    }



    public String getDecision() {
        return decision;
    }

    public void setMatch(Match playingMatch) {
        this.match = playingMatch;
    }
}
