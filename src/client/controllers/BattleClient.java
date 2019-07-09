package client.controllers;

import models.Response;
import models.map.Cell;
import models.match.Match;
import models.match.Player;

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
//
//    public static Response battleRequest() {
//        return null;
//    }

    public static void selectCard(int id) {
        ServerConnection serverConnection = new ServerConnection("/battle/select_card");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_token", playingMatch.token);
        serverConnection.parameters.put("card_id", "" + id);
        Response response = serverConnection.getResponse();
        if (response.OK) {
            playingMatch = (Match) response.data;

        } else {
            System.out.println(response.message);
        }
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
        return playingMatch.getAvailableCells();
    }

    public static Player getMe() {
        updatePlayingMatch();
        if (playingMatch.players[0].account.username.equals(AccountClient.user.username))
            return playingMatch.players[0];
        else return playingMatch.players[1];
    }

    public static boolean isMyTurn() {
        updatePlayingMatch();
        return getMe() == playingMatch.getActivePlayer();
    }

    public static boolean endTurn() {
        ServerConnection serverConnection = new ServerConnection("/battle/end_turn");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("match_token", playingMatch.token);
        Response response = serverConnection.getResponse();
        if (response.OK) {
            playingMatch = ((Match) response.data);
        }
        return response.OK;
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
}
