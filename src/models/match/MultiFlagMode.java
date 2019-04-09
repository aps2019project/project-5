package models.match;

import models.Player;
import models.items.Flag;

import java.util.ArrayList;
import java.util.List;

public class MultiFlagMode extends Match {
    List<Flag> flags = new ArrayList<>();

    @Override
    public Player getWinner() {
        return null;
    }
}
