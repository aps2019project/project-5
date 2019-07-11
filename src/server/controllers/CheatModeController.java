package server.controllers;

import javafx.stage.FileChooser;
import models.Response;
import models.match.Player;
import server.data.DataWriter;
import server.data.Files;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;


public class CheatModeController {

    public static HttpResponse incrementDrake(HttpRequest request) {
        final String amount = request.GET.get("amount");
        int intAmount = 0;
        if (amount == null)
            intAmount = 10000000;
        else
            intAmount = Integer.parseInt(amount);
        request.user.drake +=  intAmount;
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        Response response = new Response(true, "drake incremented", request.user.drake);
        return new HttpResponseJSON(response);
    }

    public static HttpResponse cheatMana(HttpRequest request) {
        final Player player = BattleController.playingMatches.get(request.GET.get("matchToken")).getActivePlayer();
        player.manaPoint +=  100;
        DataWriter.saveData(Files.USER_DATA, AuthenticationController.users);
        Response response = new Response(true, "mana incremented", player.manaPoint);
        return new HttpResponseJSON(response);
    }
}
