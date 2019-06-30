package server;

import models.cards.Collection;
import models.cards.Minion;

public class Test {
    public static void main(String... args) {
        Collection collection = new Collection();
        collection.cards.put(new Minion("", "", 100))
    }
}

