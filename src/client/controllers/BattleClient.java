package client.controllers;

import client.models.Action;
import models.Response;
import models.map.Cell;
import models.match.Match;
import models.match.Player;
import models.match.action.GameAction;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BattleClient {
    private static String token = AccountClient.user.loginToken;

    public static Match playingMatch;

    public static Match updatePlayingMatch() {
        ServerConnection serverConnection = new ServerConnection("/battle/get_match");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_token", playingMatch.token);
        Response response = serverConnection.getResponse();
        if (response.OK) playingMatch = ((Match) response.data);
        return playingMatch;
    }

    public static Response selectCard(int id) {
        ServerConnection serverConnection = new ServerConnection("/battle/select_card");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_token", playingMatch.token);
        serverConnection.parameters.put("card_id", "" + id);
        Response response = serverConnection.getResponse();
        if (response.OK) {
            playingMatch = (Match) response.data;
        }
        return response;
    }

    public static boolean move(int x, int y) {
        ServerConnection serverConnection = new ServerConnection("/battle/move");
        serverConnection.parameters.put("x", "" + x);
        serverConnection.parameters.put("y", "" + y);
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_token", playingMatch.token);
        return serverConnection.getResponse().OK;
    }

    public static Response attack(int x, int y) {
        ServerConnection serverConnection = new ServerConnection("/battle/attack");
        serverConnection.parameters.put("x", "" + x);
        serverConnection.parameters.put("y", "" + y);
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_token", playingMatch.token);
        return serverConnection.getResponse();
    }

    public static Response insert(int x, int y) {
        ServerConnection serverConnection = new ServerConnection("/battle/insert");
        serverConnection.parameters.put("x", "" + x);
        serverConnection.parameters.put("y", "" + y);
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_token", playingMatch.token);
        return serverConnection.getResponse();
    }

    public static Set<Cell> getAvailableCells() {
        return playingMatch.getAvailableCells();
    }

    public static Player getMe() {
        if (playingMatch.players[0].account.username.equals(AccountClient.user.username))
            return playingMatch.players[0];
        else return playingMatch.players[1];
    }

    public static boolean isMyTurn() {
        return getMe() == playingMatch.getActivePlayer();
    }

    public static Response endTurn() {
        ServerConnection serverConnection = new ServerConnection("/battle/end_turn");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_token", playingMatch.token);
        Response response = serverConnection.getResponse();
        if (response.OK) {
            System.out.println("turn ended");
            playingMatch = ((Match) response.data);
        }
        System.out.println("API Message: " + response.message);
        return response;
    }

    public static Response battleRequest(int match_mode) {
        ServerConnection serverConnection = new ServerConnection("/battle/request");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_mode", "" + match_mode);
        Response response = serverConnection.getResponse();
        if (response.data != null)
            playingMatch = (Match) response.data;
        return response;
    }

    public static Response opponentCheck() {
        ServerConnection serverConnection = new ServerConnection("/battle/opponent_check");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        Response response = serverConnection.getResponse();
        if (response.data != null)
            playingMatch = (Match) response.data;
        return response;
    }

    public static GameAction getAction() {
        ServerConnection serverConnection = new ServerConnection("/battle/get_action");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_token", playingMatch.token);
        Response response = serverConnection.getResponse();
        if(response.OK)
            return (GameAction) response.data;
        else
            return null;
    }
}
