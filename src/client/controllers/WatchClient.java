package client.controllers;

import models.Account;
import models.Response;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class WatchClient {
    public static ArrayList<Match> getLiveMatches() {
        ServerConnection serverConnection = new ServerConnection("/watch/get_live_matches");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        return (ArrayList<Match>) serverConnection.getResponse().data;
    }

    public static List<Account> getOnlineUsers() {
        ServerConnection serverConnection = new ServerConnection("/watch/get_online_players");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        return (List<Account>)serverConnection.getResponse().data;
    }
}
