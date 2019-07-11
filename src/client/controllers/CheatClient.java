package client.controllers;

import models.Response;

public class CheatClient {

    public static Response incrementDrake(int amount) {
        ServerConnection serverConnection = new ServerConnection("/cheat_mode/increment_drake");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("amount", String.valueOf(amount));
        return serverConnection.getResponse();
    }

    public static Response cheatMana() {
        ServerConnection serverConnection = new ServerConnection("/cheat_mode/cheat_mana");
        serverConnection.parameters.put("token", AccountClient.user.loginToken);
        serverConnection.parameters.put("matchToken", BattleClient.playingMatch.token);
        return serverConnection.getResponse();
    }

}
