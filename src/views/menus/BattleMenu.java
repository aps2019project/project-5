package views.menus;

import controllers.Manager;
import models.Collection;
import models.Player;
import models.Hand;
import models.cards.Card;
import models.cards.Minion;
import models.map.Map;
import models.match.Match;
import views.Command;
import views.Error;
import views.Output;

import java.util.List;
import java.util.regex.Matcher;

public class BattleMenu implements Menu {

    public BattleMenu() {
        // TODO: Add commands.
        commands.add(new Command(
                "^(?i)Game info$",
                "gameInfo"
        ));

        commands.add(new Command(
                "^(?i)return$",
                ""
        ));

        commands.add(new Command(
                "^(?i)show\\s+my\\s+minions",
                "showMyMinions"
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help"
        ));
        commands.add(new Command(
                "^(?i)show\\s+card(?<card>)\\s+info$",
                "showCardInfo"
        ));
        commands.add(new Command(
                "^(?i)show\\s+hand(?<hand>)",
                "showHand"
        ));
        commands.add(new Command(
                "^(?i)Move\\s+to\\s+((?<x>\\d+),(?<y>\\d+)",
                "moveTo"
        ));
        commands.add(new Command(
                "^(?i)attack\\s+(?<card>[A-z ]+",
                "attack"
        ));
        commands.add(new Command(
                "^(?i)show\\s+hand",
                "showHand"
        ));
        commands.add(new Command(
                "^(?i)insert (?<cardName>\\w+) in \\((?<x>\\d+), (?<y>\\d+)\\)$",
                "insert"
        ));
        commands.add(new Command(
                "^(?i)end turn$",
                "endTurn"
        ));

        commands.add(new Command(
                "^(?i)select\\s+(?<name>[A-z ]+)$",
                "selectCard",
                "select [CardName]",
                "select "
        ));
    }

    @Override
    public String getMenuName() {
        return "BattleMenu";
    }

    public static void gameInfo(Matcher matcher) {
        Manager.getMatchInfo();
    }

    private static void showMinions(List<Minion> minions) {
        minions.forEach(minion -> {
                    StringBuilder result = new StringBuilder();
                    result.append(minion.getID());
                    result.append(" : ");
                    result.append(minion.getName());
                    result.append(", health : ");
                    result.append(minion.getHealth());
                    result.append(", location : ");
                    result.append(String.format("(%s,%s) power : %s"
                            , minion.getCell().getX(), minion.getCell().getY(), minion.getAttackPoint()));
                    Output.log(result.toString());
                }
        );
    }

    public static void showMyMinions(Matcher matcher) {
        showMinions(Manager.showMyMinions());
    }

    public static void showOponnent(Matcher matcher) {
    }

    public static void showOpponent(Matcher matcher) {
        showMinions(Manager.showOpponentMinions());
    }

    public static void showOpponentMinions(Matcher matcher) {
    }

    public static void showCardInfo(Matcher matcher) {
        String name = matcher.group("name");
        try {
            Card card = Manager.showCardInfo(name);
            Output.log(card.showInfo());

        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND);
        }
    }

    public static void moveTo(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        try {
            Manager.moveTo(x, y);
            Card card = Manager.getActivePlayer().getSelectedCard();
            Output.log(String.format("%s moved to %d %d", card.getName(), x, y));
        } catch (Match.InvalidMoveException | Map.InvalidCellException e) {
            Output.err(Error.INVALID_MOVE);
        }

    }

    public static void attack(Matcher matcher) {
        String cardID = matcher.group("card");
        try {
            Manager.attack(cardID);
        } catch (Match.CardAttackIsNotAvailableException e) {
            Output.err(String.format(String.valueOf(Error.CARD_ATTACK_IS_NOT_AVAILABLE), e.getId()));
        } catch (Match.TiredMinionException e) {
            Output.err(String.format(String.valueOf(Error.CARD_ATTACK_IS_NOT_AVAILABLE), e.getId()));
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND);
        } catch (Match.OpponentMinionIsNotAvailableForAttack opponentMinionIsNotAvailableForAttack) {
            Output.err(Error.OPPONENT_MINION_IS_NOT_AVAILABLE);
        }
    }

    public static void useSpecialPower(Matcher matcher) {
    }

    public static void showHand(Matcher matcher) {
        Output.log("Hand:");
        Hand hand = Manager.showHand();
        hand.getCardsList().forEach(card ->
        {
            Output.log("\n\t");
            Output.log(card.getName());
        });
        Output.log("Next Card:\n\t");
        Output.log(hand.getNextCard().getName());

    }

    public static void insertCard(Matcher matcher) {
    }

    public static void endTurn(Matcher matcher) {


        //bayad kolle card hayi ke ghabileate attack darand inja attackavailability shan true mishavad!!!!

    }

    public static void showCollectibles(Matcher matcher) {
    }

    public static void selectCollectible(Matcher matcher) {
    }

    public static void showCollectibleInfo(Matcher matcher) {
    }

    public static void useCollectible(Matcher matcher) {
    }

    public static void insert(Matcher matcher) {
        String cardName = matcher.group("cardName");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        try {
            Manager.insertCard(cardName, x, y);
        } catch (Map.InvalidCellException | Collection.CollectionException | Player.NotEnoughManaException e) {
            Output.err(e);
        }
    }

    public static void help(Matcher matcher) {
        Menu.help(new BattleMenu().getCommands());
    }

    public static void selectCard(Matcher matcher) {
        String cardID = matcher.group("cardID");
        try {
            Manager.selectCard(cardID);
        } catch (Collection.CardNotFoundException e) {
            Output.err(e);
        }
    }



}
