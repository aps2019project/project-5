package client.controllers;

import client.views.Graphics;
import models.Response;
import models.map.Cell;
import models.match.Match;
import models.match.Player;

import java.util.HashSet;
import java.util.Set;

public class BattleClient {
    public static Match playingMatch;

    public static Match getPlayingMatch() {
        return null;
    }

    public static void selectCard(int id) {

    }

    public static boolean move(int row, int column) {
        return false;
    }

    public static Response attack(int row, int column) {
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

    public static Response battleRequest(int match_mode) {
        ServerConnection serverConnection = new ServerConnection("/battle/request");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_mode", "" + match_mode);
        Response response = serverConnection.getResponse();
        if(response.data != null)
             playingMatch = (Match) response.data;
        Graphics.alert("Log", "log", response.message);
        return response;
    }
}
