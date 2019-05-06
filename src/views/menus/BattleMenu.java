package views.menus;

import controllers.Manager;
import models.Collection;
import models.Player;
import models.Hand;
import models.cards.Card;
import models.cards.Minion;
import models.items.Item;
import models.map.Map;
import models.match.Match;
import views.Command;
import views.Error;
import views.Output;

import java.util.List;
import java.util.regex.Matcher;

public class BattleMenu implements Menu {

    boolean isInGraveYard = false;

    public BattleMenu() {
        commands.add(new Command(
                "^(?i)game\\s+info$",
                "gameInfo",
                "Game Info",
                "\t\t\t\t\t\tshows game details based on going match"
        ));

        commands.add(new Command(
                "^(?i)return$",
                "",
                "Return",
                "\t\t\t\t\t\t\treturns to main menu"
        ));

        commands.add(new Command(
                "^(?i)show\\s+my\\s+minions$",
                "showMyMinions",
                "Show My Minions",
                "\t\t\t\t\tshows active player minions"
        ));

        commands.add(new Command(
                "^(?i)show\\s+opponent\\s+minions$",
                "showOpponentMinions",
                "Show Opponent Minions",
                "\t\t\tShows inactive player minions"
        ));

        commands.add(new Command(
                "^(?i)help$",
                "help",
                "Help",
                "\t\t\t\t\t\t\tshows battle menu help"
        ));
        commands.add(new Command(
                "^(?i)show\\s+card\\s+(?<cardName>\\w+)\\s+info$",
                "showCardInfo",
                "Show Card [cardName] Info",
                "\t\tShows card info "
        ));
        commands.add(new Command(
                "^(?i)show\\s+hand$",
                "showHand",
                "show hand",
                "\t\t\t\t\t\tshows active player hand"
        ));
        commands.add(new Command(
                "^(?i)move\\s+to\\s+\\(\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\)$",
                "moveTo",
                "Move to ([x], [y])",
                "\t\t\t\tMoves selected card of active player to cell (x, y)"
        ));
        commands.add(new Command(
                "^(?i)attack\\s+(?<cardID>.+)$",
                "attack",
                "attack [enemyCardID]",
                "\t\t\tattacks to enemy card by the selected card"
        ));
        commands.add(new Command(
                "^(?i)insert\\s+(?<cardName>.+)\\s+in\\s+\\(\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*\\)$",
                "insert",
                "Insert [cardName] in ([x], [y])",
                "\tInserts card to cell (x, y)"

        ));
        commands.add(new Command(
                "^(?i)end\\s+turn$",
                "endTurn",
                "End Turn",
                "\t\t\t\t\t\tGives turn to opponent"
        ));

        commands.add(new Command(
                "^(?i)select\\s+(?<cardID>.+)$",
                "selectCard",
                "select [CardName]",
                "\t\t\t\tselects a card to attack"
        ));

        commands.add(new Command(
                "^(?i)show\\s+map$",
                "showMap",
                "show map",
                "\t\t\t\t\t\tprints map"
        ));

        commands.add(new Command(
                "^(?i)use\\s+special\\s+power\\s+\\(\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*\\)$",
                "useSpecialPower",
                "Use Special Power ([x], [y])",
                "\t\t\tUses special power of selected card"
        ));

        commands.add(new Command(
                "^(?i)show\\s+collectables$",
                "showCollectables",
                "Show Collectables",
                "\t\t\t\tprints collectibles items"
        ));

        commands.add(new Command(
                "^(?i)select\\s+(?<itemID>.+)$",
                "selectCollectable",
                "Select [collectableID]",
                "\t\t\tSelects collectable item"
        ));

        commands.add(new Command(
                "^(?i)show\\s+info$",
                "showInfo",
                "Show Info",
                "\t\t\t\t\tShows info of the selected collectable item"
        ));

        commands.add(new Command(
                "^(?i)use\\s+\\(\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*\\)$",
                "useCollectableItem",
                "Use ([x], [y])",
                "\t\t\tUses selected collectable item in (x, y)"
        ));

        commands.add(new Command(
                "^(?i)show\\s+next\\s+card$",
                "showNextCard",
                "Show Next Card",
                "\t\t\t\t\tPrints next card that is going to be added to hand"
        ));

        commands.add(new Command(
                "^(?i)enter\\s+graveyard$",
                "enterGraveyard",
                "Enter Graveyard",
                "\t\t\t\t\tEnters graveyard"
        ));

        commands.add(new Command(
                "^(?i)show\\s+info\\s+(?<cardID>\\d+)$",
                "showInfoInGraveyard",
                "Show Info [cardID]",
                "\t\t\t\t\tPrints info about a card in graveyard"
        ));

        commands.add(new Command(
                "^(?i)show\\s+cards$",
                "showCards",
                "Show Cards",
                "\t\t\t\t\tPrints cards of graveyard"
        ));

        commands.add(new Command(
                "^(?i)end\\s+game$",
                "",
                "End Game",
                "\t\t\t\t\t turns back to the battle menu in the end of match"
        ));

        commands.add(new Command(
                "^(?i)exit",
                "exit",
                "Exit",
                "Exits from graveyard"
        ));


        // TODO: 5/7/19 add help command

        // TODO: 5/7/19 add combo attack command

    }

    @Override
    public String getMenuName() {
        return "BattleMenu";
    }

    public static void gameInfo(Matcher matcher) {
        Output.log(Manager.getMatchInfo());
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

    public static void showOpponentMinions(Matcher matcher) {
        showMinions(Manager.showOpponentMinions());
    }

    public static void showCardInfo(Matcher matcher) {
        String cardName = matcher.group("cardName");
        try {
            Card card = Manager.showCardInfo(cardName);
            Output.log( card.showInfo());
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND_IN_COLLECTION);
        }
    }

    public static void showMap(Matcher matcher) {
        Output.log(Manager.getMap().toString());
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
        String cardID = matcher.group("cardID");
        try {
            Manager.attack(cardID);
        } catch (Match.CardAttackIsNotAvailableException e) {
            Output.err(String.format(String.valueOf(Error.CARD_ATTACK_IS_NOT_AVAILABLE), e.getId()));
        } catch (Match.TiredMinionException e) {
            Output.err(String.format(String.valueOf(Error.CARD_ATTACK_IS_NOT_AVAILABLE), e.getId()));
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND_IN_COLLECTION);
        } catch (Match.OpponentMinionIsNotAvailableForAttack opponentMinionIsNotAvailableForAttack) {
            Output.err(Error.OPPONENT_MINION_IS_NOT_AVAILABLE);
        }
    }

    public static void useSpecialPower(Matcher matcher) {

    }

    public static void showHand(Matcher matcher) {
        Output.log("Hand:");
        Hand hand = Manager.getHand();
        hand.getCards().forEach(card -> Output.log("\t" + card.getID()));
        Output.log("Next Card:");
        Output.log("\t" + hand.getNextCard().getName());

    }

    public static void endTurn(Matcher matcher) {
        Manager.endTurn();

        //bayad kolle card hayi ke ghabileate attack darand inja attackavailability shan true mishavad!!!!

    }

    public static void showCollectables(Matcher matcher) {
        List<Item> collectableItems = Manager.getCollectableItems();
        Output.log(collectableItems);
    }

    public static void selectCollectable(Matcher matcher) {
        String itemID = matcher.group("itemID");
        Manager.selectCollectableItem(itemID);
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
            Output.err(Error.CARD_NOT_IN_HAND);
        } catch (Map.InvalidTargetCellException | Player.HeroDeadException e) {
            e.printStackTrace();
        }
    }

    public static void help(Matcher matcher) {
        Menu.help(commands);
    }

    public static void selectCard(Matcher matcher) {
        String cardID = matcher.group("cardID");
        try {
            Manager.selectCard(cardID);
            Output.log("card selected!");
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND);
        }
    }

    public void showInfo(Matcher matcher) {
        try {
            Output.log(Manager.getSelectedCollectableItem());
        } catch (Player.NoItemSelectedException e) {
            Output.err(Error.NO_ITEM_SELECTED);
        }
    }

    public void useCollectableItem(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Manager.useCollectableItem(x, y);
    }

    public void showNextCard(Matcher matcher) {
        Card nextCard = Manager.getNextCard();
        Output.log(nextCard);
    }

    public void enterGraveyard(Matcher matcher) {
        if (isInGraveYard) {
            Output.err(Error.ALREADY_IN_GRAVEYARD);
        }
        isInGraveYard = true;
    }

    public void exit(Matcher matcher) {
        if (!isInGraveYard) {
            Output.err(Error.NOT_IN_GRAVEYARD);
        }
        isInGraveYard = false;
    }

    public void showInfoInGraveyard(Matcher matcher) {
        if (!isInGraveYard) {
            Output.err(Error.NOT_IN_GRAVEYARD);
            return;
        }
        String cardID = matcher.group("cardID");
        try {
            Manager.getCardInGraveyard(cardID);
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND_IN_GRAVEYARD);
        }
    }

    public void showCards(Matcher matcher) {
        if (!isInGraveYard) {
            Output.err(Error.NOT_IN_GRAVEYARD);
            return;
        }
        List<Card> cards = Manager.getCardsInGraveyard();
        Output.log("Graveyard Cards :");
        cards.forEach(Output::log);
    }
}
