package client.controllers;

import models.Response;
import models.match.Match;

import java.util.ArrayList;

public class WatchClient {
    public static ArrayList<Match> getLiveMatches() {
        ServerConnection serverConnection = new ServerConnection("/watch/get_live_matches");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        return (ArrayList<Match>) serverConnection.getResponse().data;
    }
}
