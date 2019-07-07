package client.controllers;

import models.Response;
import models.cards.Card;
import models.map.Cell;
import models.match.Match;
import models.match.Player;
import server.controllers.AuthenticationController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BattleClient {
    private static String token = AccountClient.user.loginToken;

    public static Match getPlayingMatch() {
        Response response;
        ServerConnection serverConnection = new ServerConnection("/battle/");
        return null;
    }

    public static Response battleRequest() {
        return null;
    }

    public static void selectCard(int id) {

    }

    public static boolean move(int row, int column) {
        return false;
    }

    public static Response attack(int x, int y) {
        return null;
    }

    public static Response insert(int row, int column) {
        return null;
    }

    public static Set<Cell> getAvailableCells() {
        return new HashSet<>();
    }

    public static Player getMe() {
        return null;
    }
}
