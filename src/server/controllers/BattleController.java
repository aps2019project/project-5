package server.controllers;

import models.Account;
import models.Response;
import models.match.*;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

import java.util.HashMap;

public class BattleController {
    public static Account waitingUserForDeathMatch;
    public static Account waitingUserForCaptureTheFlagMatchMatch;
    public static Account waitingUserForMultiFlagMatch;
    public static HashMap<String, Match> playingMatches = new HashMap<>();

    public static Account getWaitingUser(MatchMode matchMode) {
        if (matchMode == MatchMode.MULTI_FLAG_MATCH)
            return waitingUserForMultiFlagMatch;
        if (matchMode == MatchMode.CAPTURE_THE_FLAG_MATCH)
            return waitingUserForCaptureTheFlagMatchMatch;
        return waitingUserForDeathMatch;
    }

    public static void setWaitingUser(MatchMode matchMode, Account user) {
        if (matchMode == MatchMode.CAPTURE_THE_FLAG_MATCH)
            waitingUserForCaptureTheFlagMatchMatch = user;
        if (matchMode == MatchMode.DEATH_MATCH)
            waitingUserForDeathMatch = user;
        if (matchMode == MatchMode.MULTI_FLAG_MATCH)
            waitingUserForMultiFlagMatch = user;
    }

    public static HttpResponse battleRequest(HttpRequest request) {
        String matchModeString = request.GET.get("match_mode");
        Response response;
        if (matchModeString == null)
            response = new Response(false, "match_mode not sent :(");
        else try {
            int matchModeInt = Integer.valueOf(matchModeString);
            MatchMode matchMode = MatchMode.getByInt(matchModeInt);
            Account waitingAccount = getWaitingUser(matchMode);
            if (waitingAccount == null) {
                setWaitingUser(matchMode, request.user);
                response = new Response(true, "wait for opponent!! :))");
            } else if (waitingAccount.equals(request.user)) {
                response = new Response(false, "you are already waited for opponent :).");
            } else {
                String matchToken = AuthenticationController.randomString(30);
                switch (matchMode) {
                    case DEATH_MATCH:
                        playingMatches.put(matchToken, new DeathMatch(waitingUserForDeathMatch, request.user));
                        break;
                    case MULTI_FLAG_MATCH:
                        playingMatches.put(matchToken, new MultiFlagMatch(waitingUserForMultiFlagMatch, request.user));
                        break;
                    case CAPTURE_THE_FLAG_MATCH:
                        playingMatches.put(matchToken, new CaptureTheFlagMatch(waitingUserForCaptureTheFlagMatchMatch, request.user));
                        break;
                }
                playingMatches.get(matchToken).token = matchToken;
                response = new Response(true, "let's play the game with: ", playingMatches.get(matchToken));

            }
        } catch (Exception e) {
            response = new Response(false, "match mode must be 1 or 2 or 3");
        }
        return new HttpResponseJSON(response);
    }

    public static HttpResponse cancelBattleRequest(HttpRequest request) {
        Response response = new Response(true, "battle request canceled");
        if (request.user.equals(waitingUserForCaptureTheFlagMatchMatch))
            waitingUserForMultiFlagMatch = null;
        else if (request.user.equals(waitingUserForDeathMatch))
            waitingUserForDeathMatch = null;
        else if (request.user.equals(waitingUserForMultiFlagMatch))
            waitingUserForMultiFlagMatch = null;
        else
            response = new Response(false, "you don't have any battle request");
        return new HttpResponseJSON(response);

    }
}
