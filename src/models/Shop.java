package models;

import models.cards.AttackType;
import models.cards.Card;
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;
import models.exceptions.AccountNotFoundException;
import models.exceptions.CardNotFoundException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Shop {
    private static Collection cardsCollection = new Collection();

    private static List<String> orderList = new LinkedList<>(Arrays.asList(
            "class Hero",
            "class Item",
            "class Minion",
            "class Spell"));

    static {
        // TODO: add cards to cardsCollection Collection.

        cardsCollection.addMember(new Minion(
        cardsCollection.addMember(new Minion(
                "Persian Archer",
                "",
                2,
                300,
                6,
                4,
                AttackType.RANGED,
                7,
                null
        ));

        cardsCollection.addMember(new Minion(
                "Persian Swordsman",
                "",
                2,
                400,
                6,
                4,
                AttackType.MELEE,
                0,
                SpecialPowerActivateTime.ON_ATTACK
        )); // Special Power must be added!

        cardsCollection.addMember(new Minion(
                "Persian Lancer",
                "",
                1,
                500,
                5,
                3,
                AttackType.HYBRID,
                3,
                null
        ));
    }

    public static String cardsToString() {
        String result = "";
        for (int j = 0; j < orderList.size(); j++) {
            result += orderList.get(j).replace("class ", "") + "\n";
            for (int i = 1; i < cardsCollection.getCards().size(); i++) {
                Card card = (Card) cardsCollection.getCards().get(i);
                if (!card.getClass().toString().equals(orderList.get(j)))
                    continue;
                if(i > 1)
                    result += "\n";
                result += "\t\t" + i + " : " + card + " " + card.getPrice() + "$\n" ;
            }
        }
    }

    public Collection getCardsCollection() {
        return cardsCollection;
    }


    public void sell(Account account, Card card) throws AccountNotFoundException, CardNotFoundException {

    }
}
