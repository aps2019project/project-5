package server.controllers;

import models.Account;
import models.Response;
import models.match.*;
import models.match.action.Move;
import server.models.http.HttpRequest;
import server.models.http.HttpResponse;
import server.models.http.HttpResponseJSON;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class BattleController {
    private static final int MATCH_TOKEN_LENGTH = 5;
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
                String matchToken = AuthenticationController.randomString(MATCH_TOKEN_LENGTH);
                switch (matchMode) {
                    case DEATH_MATCH:
                        playingMatches.put(matchToken, new DeathMatch(waitingUserForDeathMatch, request.user));
                        waitingUserForDeathMatch = null;
                        break;
                    case MULTI_FLAG_MATCH:
                        playingMatches.put(matchToken, new MultiFlagMatch(waitingUserForMultiFlagMatch, request.user));
                        waitingUserForMultiFlagMatch = null;
                        break;
                    case CAPTURE_THE_FLAG_MATCH:
                        playingMatches.put(matchToken, new CaptureTheFlagMatch(waitingUserForCaptureTheFlagMatchMatch, request.user));
                        waitingUserForCaptureTheFlagMatchMatch = null;
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

    public static HttpResponse selectCard(HttpRequest request) {
        Response response;
        String matchToken = request.GET.get("match_token");
        if (matchToken == null)
            response = new Response(false, "match_token not sent.", 100);
        else {
            Match match = playingMatches.get(matchToken);
            if (match == null)
                response = new Response(false, "invalid match_token!!");
            else {
                if (match.getActivePlayer().account.username.equals(request.user.username)) {
                    String cardIdStr = request.GET.get("card_id");
                    if (cardIdStr == null)
                        response = new Response(false, "card_id not sent.", 100);
                    else try {
                        int cardId = Integer.parseInt(cardIdStr);
                        if (match.selectCard(cardId)) {
                            response = new Response(true, "card selected successfully.", match);
                        } else {
                            response = new Response(false, "can't select this card.");
                        }
                    } catch (Throwable e) {
                        System.err.println(e.getMessage());
                        e.printStackTrace();
                        response = new Response(false, "card_id must be integer");
                    }
                } else
                    response = new Response(false, "It isn't your turn :(");
            }
        }
        return new HttpResponseJSON(response);
    }

    public static HttpResponse endTurn(HttpRequest request) {
        Response response;
        String matchToken = request.GET.get("match_token");
        if (matchToken == null)
            response = new Response(false, "match_token not sent.", 100);
        else {
            Match match = playingMatches.get(matchToken);
            if (match == null)
                response = new Response(false, "invalid match_token!!");
            else {
                if (match.getActivePlayer().account.username.equals(request.user.username)) {
                    match.endTurn();
                    response = new Response(true, "your turn ended", match);
                } else
                    response = new Response(false, "It isn't your turn :(");
            }
        }
        return new HttpResponseJSON(response);
    }

    public static HttpResponse getMatch(HttpRequest request) {
        Response response;
        String matchToken = request.GET.get("match_token");
        if (matchToken == null)
            response = new Response(false, "match_token not sent.", 100);
        else {
            Match match = playingMatches.get(matchToken);
            if (match == null)
                response = new Response(false, "invalid match_token!!");
            else {
                response = new Response(true, "see match data.", match);
            }
        }
        return new HttpResponseJSON(response);
    }

    public static HttpResponse insert(HttpRequest request) {
        Response response;
        String matchToken = request.GET.get("match_token");
        if (matchToken == null)
            response = new Response(false, "match_token not sent.", 100);
        else {
            Match match = playingMatches.get(matchToken);
            if (match == null)
                response = new Response(false, "invalid match_token!!");
            else {
                if (match.getActivePlayer().account.username.equals(request.user.username)) {
                    try {
                        int x = Integer.valueOf(request.GET.get("x"));
                        int y = Integer.valueOf(request.GET.get("y"));
                        if (match.insertCard(x, y)) {
                            response = new Response(true, "card inserted!", match);
                        } else {
                            response = new Response(false, "cant insert the card");
                        }
                    } catch (Throwable e) {
                        response = new Response(false, "x or y is not valid");
                    }
                } else {
                    response = new Response(false, "It isn't your turn :(", match);
                }
            }
        }
        return new HttpResponseJSON(response);
    }

    public static HttpResponse getAction(HttpRequest request) {
        Response response;
        String matchToken = request.GET.get("match_token");
        if (matchToken == null)
            response = new Response(false, "match_token not sent.", 100);
        else {
            Match match = playingMatches.get(matchToken);
            if (match == null)
                response = new Response(false, "invalid match_token!!");
            else {
                if (match.players[0].account.username.equals(request.user.username)) {
                    response = new Response(true, "oldest action sent", match.player2Actions.pollLast());
                } else {
                    response = new Response(true, "oldest action sent", match.player1Actions.pollLast());
                }
            }
        }
        return new HttpResponseJSON(response);
    }

    public static HttpResponse moveCard(HttpRequest request) {
        Response response;
        String matchToken = request.GET.get("match_token");
        if (matchToken == null)
            response = new Response(false, "match_token not sent.", 100);
        else {
            Match match = playingMatches.get(matchToken);
            if (match == null)
                response = new Response(false, "invalid match_token!!");
            else {
                if (match.getActivePlayer().account.username.equals(request.user.username)) {
                    try {
                        int x = Integer.valueOf(request.GET.get("x"));
                        int y = Integer.valueOf(request.GET.get("y"));
                        if (match.moveCard(x, y)) {
                            response = new Response(true, "card move!", match);
                        } else {
                            response = new Response(false, "cant move the card");
                        }
                    } catch (Throwable e) {
                        response = new Response(false, "x or y is not valid");
                    }
                } else {
                    response = new Response(false, "It isn't your turn :(", match);
                }
            }
        }
        return new HttpResponseJSON(response);
    }

    public static HttpResponse attack(HttpRequest request) {
        Response response;
        String matchToken = request.GET.get("match_token");
        if (matchToken == null)
            response = new Response(false, "match_token not sent.", 100);
        else {
            Match match = playingMatches.get(matchToken);
            if (match == null)
                response = new Response(false, "invalid match_token!!");
            else {
                if (match.getActivePlayer().account.username.equals(request.user.username)) {
                    try {
                        int x = Integer.valueOf(request.GET.get("x"));
                        int y = Integer.valueOf(request.GET.get("y"));
                        int res = match.attack(x, y);
                        if (res == 1) {
                            response = new Response(true, "attack without counter!", res);
                        } else if (res == 2) {
                            response = new Response(true, "attack with felan!", res);
                        } else {
                            response = new Response(false, "invalid attack!", res);
                        }
                    } catch (Throwable e) {
                        response = new Response(false, "x or y is not valid");
                    }
                } else {
                    response = new Response(false, "It isn't your turn :(", match);
                }
            }
        }
        return new HttpResponseJSON(response);
    }

    public static HttpResponse opponent_check(HttpRequest request) {
        AtomicReference<Response> response = new AtomicReference<>(
                new Response(true, "match not started")
        );
        playingMatches.forEach((token, match) -> {
            if (match.players[0].account.username.equals(request.user.username) ||
                    match.players[1].account.username.equals(request.user.username)) {
                response.set(new Response(true, "match started!", match));
            }
        });
        return new HttpResponseJSON(response.get());
    }

}
