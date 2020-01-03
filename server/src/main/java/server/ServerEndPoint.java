package server;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import enums.Color;
import enums.Rank;
import helpers.StrategoGame;
import interfaces.IGame;
import interfaces.StrategoServer;
import messageenum.MessageType;
import messages.*;

import java.awt.Point;


import java.util.*;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/stratego")
public class ServerEndPoint implements StrategoServer {
    // All sessions
    private static final List<Session> connectedPlayers = new ArrayList<>();
    private StrategoLogin strategoLogin = new StrategoLogin();
    private static List<IGame> games = new ArrayList<>();
    // Map each property to list of sessions that are subscribed to that property
    private static final Map<Integer, List<Session>> strategoGames = new HashMap<>();
    private int gameId = 0;
    private Gson gson = new Gson();

    @OnOpen
    public void onConnect(Session session) {
        System.out.println("[WebSocket Connected] SessionID: " + session.getId());
        String message = String.format("[New client with client side session ID]: %s", session.getId());
        connectedPlayers.add(session);
        System.out.println("[#sessions]: " + connectedPlayers.size());
    }

    @OnMessage
    public void onText(String message, Session session) {
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Received] : " + message);
        handleMessageFromClient(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Socket Closed]: " + reason);
        connectedPlayers.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        System.out.println("[WebSocket Session ID] : " + session.getId() + "[ERROR]: ");
        cause.printStackTrace(System.err);
    }

    // Handle incoming message from client
    private void handleMessageFromClient(String jsonMessage, Session session) {
        Gson gson = new Gson();
        Message wbMessage = null;
        try {
            wbMessage = gson.fromJson(jsonMessage, Message.class);
        } catch (JsonSyntaxException ex) {
            System.out.println("[WebSocket ERROR: cannot parse Json message " + jsonMessage);
            return;
        }

        // Operation defined in message


        // Process message based on operation

        if (null != wbMessage) {
            switch (wbMessage.getMessageType()) {
                case USERREADY:
                    // Find game

                    if (!isUserInGame(session)) {
                        findGameForUser(session);
                    } else {
                        int gameid = getKeyBasedOnSession(session);
                        if (games.get(gameid).haveBothPlayersPlacedAllUnits()) {
                            games.get(gameid).startGamePlayingPhase();
                            sendOperationToBoth(session, null, MessageType.STARTPLAYINGPHASE);
                        }
                    }
                    break;
                case UNREGISTERFROMGAME:
                    // Game done
                    unregisterFromGame(session);
                    break;
                case DELETE:
                    // Send game operation to client
                    if (wbMessage.getResult() == null || wbMessage.getResult() == "") break;
                    sendOperationToOpponent(session, wbMessage.getResult(), MessageType.DELETE);
                    break;
                case PLACEUNIT:
                    PlaceUnitMessage placeunit = gson.fromJson(wbMessage.getResult(), new TypeToken<PlaceUnitMessage>() {
                    }.getType());
                    Color color = placeunit.getColor() == 1 ? Color.RED : Color.BLUE;
                    int gameid = getKeyBasedOnSession(session);
                    if (games.get(gameid).placePiece(Integer.parseInt(session.getId()), Rank.valueOf(placeunit.getRank()), placeunit.getCoordsToPlace().x, placeunit.getCoordsToPlace().y, color)) {
                       sendOperationToBoth(session, wbMessage.getResult(),MessageType.PLACEUNIT);
                    }
                case MOVEMENT:
                    int gameid2 = getKeyBasedOnSession(session);
                    MovementMessage movementMessage = gson.fromJson(wbMessage.getResult(), new TypeToken<MovementMessage>() {
                    }.getType());
                    games.get(gameid2).movePiece(movementMessage.getOldCoords(), movementMessage.getNewCoords());


                default:
                    System.out.println("[WebSocket ERROR: cannot process Json message " + jsonMessage);
                    break;
            }
        }
    }

    private int getKeyBasedOnSession(Session session) {
        for (Map.Entry<Integer, List<Session>> gameSession : strategoGames.entrySet()) {
            if (gameSession.getValue().contains(session)) {
                return gameSession.getKey() - 1;
            }
        }
        return -1;
    }

    private void sendOperationToOpponent(Session session, String content, MessageType mt) {
        for (Map.Entry<Integer, List<Session>> gameSession : strategoGames.entrySet()) {
            if (gameSession.getValue().contains(session)) {
                for (Session s : gameSession.getValue()) {
                    if (s != session) {
                        Message webSocketMessage = new Message(mt, content);
                        s.getAsyncRemote().sendText(gson.toJson(webSocketMessage));
                    }
                }
            }
        }
    }

    private void sendOperationToBoth(Session session, String content, MessageType mt) {
        for (Map.Entry<Integer, List<Session>> gameSession : strategoGames.entrySet()) {
            if (gameSession.getValue().contains(session)) {
                for (Session s : gameSession.getValue()) {

                    Message webSocketMessage = new Message(mt, content);
                    s.getAsyncRemote().sendText(gson.toJson(webSocketMessage));

                }
            }
        }
    }

    private void unregisterFromGame(Session session) {
        int toRemoveIndex = -1;
        int gameId = -1;
        for (Map.Entry<Integer, List<Session>> gameSession : strategoGames.entrySet()) {
            for (Session s : gameSession.getValue()) {
                if (s == session) {
                    gameId = gameSession.getKey() - 1;
                    toRemoveIndex = gameSession.getValue().indexOf(s);
                    System.out.println("Removed session: " + session.getId() + " from gameId: " + (gameSession.getKey() - 1));
                    break;
                }
            }
        }
        strategoGames.get(gameId).remove(toRemoveIndex);
    }//

    private void findGameForUser(Session session) {
        for (Map.Entry<Integer, List<Session>> gameSession : strategoGames.entrySet()) {
            if (gameSession.getValue().size() < 2) {  //er zit een speler in de lobby die wacht op een opponent
                gameSession.getValue().add(session);
                notifyGameFound(gameSession);
                games.add(new StrategoGame(gameSession.getKey() - 1, this));
                games.get(gameSession.getKey() - 1).startGamePlanningPhase();
                return;
            }
        }
        //game not found, create new lobby
        gameId++;
        strategoGames.put(gameId, new ArrayList<>());
        strategoGames.get(gameId).add(session);
    }

    private boolean isUserInGame(Session session) {
        for (Map.Entry<Integer, List<Session>> gameSession : strategoGames.entrySet()) {
            if (gameSession.getValue().contains(session)) {
                return true;
            }
        }
        return false;
    }

    private void notifyGameFound(Map.Entry<Integer, List<Session>> gameSession) {
        int sessionvalue = 1;
        Gson gson = new Gson();
        for (Session s : gameSession.getValue()) {


            Message webSocketMessage = new Message(MessageType.MATCHFOUND, gson.toJson(sessionvalue));
            sessionvalue++;
            System.out.println("[WebSocket send ] " + webSocketMessage + " to:" + s.getId());
            System.out.println("[WebSocket send ] " + webSocketMessage + " to:" + s.getId());

            s.getAsyncRemote().sendText(gson.toJson(webSocketMessage));
        }


    }

    @Override
    public void moveUnit(Point oldPos, Point newPos, int gameId){
    sendMessageWithMessageType(new Message(MessageType.MOVEMENT, gson.toJson(new MovementMessage(oldPos, newPos))), gameId);

    }

    @Override
    public void deleteUnit(Point Pos, int gameId) {

        sendMessageWithMessageType(new Message(MessageType.DELETE, gson.toJson(new DeleteMessage(Pos))), gameId);


    }

    private void sendMessageWithMessageType(Message message, int gameId) {
        Gson gson = new Gson();
        for (Map.Entry<Integer, List<Session>> gameSession : strategoGames.entrySet()) {
            if (gameSession.getKey() - 1 == gameId) {
                for (Session s : gameSession.getValue()) {
                    s.getAsyncRemote().sendText(gson.toJson(message));
                }
                break;
            }
        }
    }

    @Override
    public void endGame(Color teamcolor, int gameId) {
        int color = teamcolor == Color.RED ? 1 : 2;
       // unregisterFromGame();
        sendMessageWithMessageType(new Message(MessageType.ENDGAME, gson.toJson(new EndGameMessage(color))), gameId);
    }

    @Override
    public void sendMessageNotYourTurn(String string, Color turnColor, int gameId) {
        int color = turnColor == Color.RED ? 2 : 1; // opposite because it goes to the requesting player, making him aware that it's not his turn.
        sendMessageWithMessageType(new Message(MessageType.NOTYOURTURN, gson.toJson(new NotYourTurnMessage(color))), gameId);

    }
}
