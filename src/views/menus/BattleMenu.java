package views.menus;

import controllers.ClientManager;
import controllers.logic.Manager;
import models.Collection;
import models.Player;
import models.Hand;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.items.CollectableItem;
import models.items.Item;
import models.map.Map;
import models.match.Match;
import views.Command;
import views.Error;
import views.InputAI;
import views.Output;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;

public class BattleMenu implements Menu {

    private static boolean isInGraveYard = false;

    public List<Command> getAICommands() {
        return commands;
    }

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
                "^(?i)move\\s+to\\s+\\(?\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*\\)?$",
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
                "^(?i)insert\\s+(?<cardName>.+)\\s+in\\s+\\(?\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*\\)?$",
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
                "^(?i)select\\s+(?<id>.+)$",
                "select",
                "select [cardID | collectableItemID]",
                "\t\t\t\tselects a card or a collectable item to attack"
        ));

        commands.add(new Command(
                "^(?i)show\\s+map$",
                "showMap",
                "show map",
                "\t\t\t\t\t\tprints map"
        ));

        commands.add(new Command(
                "^(?i)use\\s+special\\s+power\\s+\\(?\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*\\)?$",
                "useSpecialPower",
                "Use Special Power ([x], [y])",
                "\tUses special power of selected card"
        ));

        commands.add(new Command(
                "^(?i)show\\s+collectables$",
                "showCollectables",
                "Show Collectables",
                "\t\t\t\tprints collectibles items"
        ));

        commands.add(new Command(
                "^(?i)show\\s+info$",
                "showInfo",
                "Show Info",
                "\t\t\t\t\t\tShows info of the selected collectable item"
        ));

        commands.add(new Command(
                "^(?i)use\\s+\\(?\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*\\)?$",
                "useCollectableItem",
                "Use ([x], [y])",
                "\t\t\t\t\tUses selected collectable item in (x, y)"
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
                "^(?i)show\\s+my\\s+hero$",
                "showMyHero",
                "Show My Hero",
                "\t\t\t\tShow Active player's hero."
        ));

        commands.add(new Command(
                "^(?i)show\\s+info\\s+(?<cardID>.+)$",
                "showInfoInGraveyard",
                "Show Info [cardID]",
                "\t\t\t\tPrints info about a card in graveyard"
        ));

        commands.add(new Command(
                "^(?i)show\\s+cards$",
                "showCards",
                "Show Cards",
                "\t\t\t\t\t\tPrints cards of graveyard"
        ));

        commands.add(new Command(
                "^(?i)end\\s+game$",
                "",
                "End Game",
                "\t\t\t\t\t\tturns backToMultiSingle to the battle menu in the end of match"
        ));

        commands.add(new Command(
                "^(?i)exit( graveyard)?$",
                "exit",
                "Exit",
                "\t\t\t\t\t\t\tExits from graveyard"
        ));

        // TODO: 5/7/19 add help command

        // TODO: 5/7/19 add combo attack command

    }

    @Override
    public void handleMenu() {
        while(true) {
            String inputCommand ;
            if (Manager.isAITurn()){
                inputCommand = InputAI.getInstance().getCommand();
            }
            else
                inputCommand = Manager.getInput().getCommand(getMenuName());
            boolean matches = false;
            for(Command command : getCommands()) {
                Matcher matcher = command.getPattern().matcher(inputCommand);
                if(matcher.find()) {
                    if(command.getFunctionName().equals(""))
                        return;
                    matches = true;
                    Method method ;
                    try {
                        method = getClass().getMethod(command.getFunctionName(), Matcher.class);
                        Object object = method.invoke(null, matcher);
                        if(object != null && object.equals(Boolean.FALSE))
                            return;
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            if(!matches)
                Output.err(Error.INVALID_COMMAND.toString());
        }
    }

    @Override
    public String getMenuName() {
        return "BattleMenu";
    }

    public static void gameInfo(Matcher matcher) {
        Output.log(Manager.getMatchInfo());
    }

    private static void showMinions(List<Minion> attackers) {
        attackers.forEach(minion -> {
                String result = minion.getID() +
                        " : " +
                        minion.getName() +
                        ", health : " +
                        minion.getHealth() +
                        ", location : " +
                        String.format("(%s,%s) power : %s"
                                , minion.getCell().getX() + 1, minion.getCell().getY() + 1, minion.getAttackPoint());
                Output.log(result);
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
            Output.log(card.showInfo());
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
        } catch (Match.InvalidMoveException e) {
            Output.err(Error.INVALID_MOVE);
        } catch (Map.InvalidCellException e) {

        }
    }

    public static boolean attack(Matcher matcher) {
        String cardID = matcher.group("cardID");
        try {
            Manager.attack(cardID);
            if(Manager.getPlayingMatch() == null) {
                Output.log("Player " + Manager.getWinner().getUsername() + " wins.");
                return false;
            }
        } catch (Match.CardAttackIsNotAvailableException e) {
            Output.err(String.format(String.valueOf(Error.CARD_ATTACK_IS_NOT_AVAILABLE), e.getId()));
        } catch (Match.TiredMinionException e) {
            Output.err(String.format(String.valueOf(Error.CARD_ATTACK_IS_NOT_AVAILABLE), e.getId()));
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND_IN_COLLECTION);
        } catch (Match.OpponentMinionIsNotAvailableForAttack opponentMinionIsNotAvailableForAttack) {
            Output.err(Error.OPPONENT_MINION_IS_NOT_AVAILABLE);
        } catch (Player.CardNotSelectedException e) {
            Output.err(Error.CARD_NOT_SELECTED);
        }
        return true;
    }

    public static void useSpecialPower(Matcher matcher) {

    }

    public static void showHand(Matcher matcher) {
        Output.log("Hand:");
        Hand hand = Manager.getHand();
        hand.getCards().forEach(card -> Output.log("\t" + card.getID() + "\t Mana: " + card.getManaPoint()));
        Output.log("Next Card:");
        Output.log("\t" + hand.getNextCard().getID() + "\t Mana: " + hand.getNextCard().getManaPoint());
        Output.log("Mana: " + Manager.getActivePlayer().getMana());
    }

    public static void endTurn(Matcher matcher) {
        Manager.endTurn();
        //bayad kolle card hayi ke ghabileate attack darand inja attackavailability shan true mishavad!!!!
    }

    public static void showCollectables(Matcher matcher) {
        List<Item> collectibleItems = Manager.getCollectableItems();
        Output.log(collectibleItems);
    }

    public static void insert(Matcher matcher) {
        String cardName = matcher.group("cardName");
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        try {
            Manager.insertCard(cardName, x, y);
        } catch (Player.NotEnoughManaException e) {
            Output.err(Error.NOT_ENOUGH_MANA);
        } catch (Player.HeroDeadException e) {
            Output.err(e.getMessage());
        } catch (Map.InvalidCellException e) {
            Output.err(e.getMessage());
        } catch (Collection.CardNotFoundException e) {
            Output.err(Error.CARD_NOT_FOUND);
        } catch (Map.InvalidTargetCellException e) {
            e.printStackTrace();
        }
    }

    public static void help(Matcher matcher) {
        Menu.help(commands);
    }

    public static void select(Matcher matcher) {
        String id = matcher.group("id");
        try {
            ClientManager.selectCard(id);
            Output.log(Error.CARD_SELECTED.toString());
        } catch (Collection.CardNotFoundException e) {
            boolean flag = false;
            try {
                Manager.selectCollectableItem(id);
                Output.log(Error.COLLECTABLE_ITEM_SELECTED.toString());
                flag = true;
            } catch (Player.ItemNotFoundException e1) {
                Output.err(Error.NO_ITEM);
            }
            if (!flag)
                Output.err(Error.CARD_NOT_FOUND);
        }
    }

    public static void showInfo(Matcher matcher) {
        try {
            Output.log(Manager.getSelectedCollectableItem());
        } catch (Player.NoItemSelectedException e) {
            Output.err(Error.NO_SELECTABLE_ITEM_SELECTED);
        }
    }

    public static void useCollectableItem(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        try {
            Manager.useCollectableItem(x, y);
        } catch (CollectableItem.NoCollectableItemSelected noCollectableItemSelected) {
            Output.err(noCollectableItemSelected.getMessage());
        }
    }

    public static void showNextCard(Matcher matcher) {
        Card nextCard = Manager.getNextCard();
        Output.log(nextCard);
    }

    public static void enterGraveyard(Matcher matcher) {
        if (isInGraveYard) {
            Output.err(Error.ALREADY_IN_GRAVEYARD);
        }
        isInGraveYard = true;
    }

    public static void exit(Matcher matcher) {
        if (!isInGraveYard) {
            Output.err(Error.NOT_IN_GRAVEYARD);
        }
        isInGraveYard = false;
    }

    public static void showInfoInGraveyard(Matcher matcher) {
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

    public static void showCards(Matcher matcher) {
        if (!isInGraveYard) {
            Output.err(Error.NOT_IN_GRAVEYARD);
            return;
        }
        List<Card> cards = Manager.getCardsInGraveyard();
        Output.log("Graveyard Cards :");
        cards.forEach(Output::log);
    }


    public static void showMyHero(Matcher matcher) {
        try {
            Hero hero = Manager.getActivePlayer().getHero();
            Output.log(hero.getID() +
                    " : " +
                    hero.getName() +
                    ", health : " +
                    hero.getCurrentHealth() +
                    ", location : " +
                    String.format("(%s,%s) power : %s"
                            , hero.getCell().getX() + 1, hero.getCell().getY() + 1, hero.getAttackPoint()));
        } catch (Player.HeroDeadException e) {
            Output.err(Error.HERO_DEATH);
        }
    }
}
