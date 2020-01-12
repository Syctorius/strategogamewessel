package server;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import enums.Color;
import enums.Rank;
import helpers.CommunicatorServer;
import helpers.Piece;
import helpers.StrategoGame;
import interfaces.IGame;
import interfaces.StrategoServer;
import messageenum.MessageType;
import messagefactory.DefaultFactory;
import messagefactory.PlaceUnitFactory;
import messages.*;

import java.awt.Point;


import java.io.IOException;
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
    private static List<IGame> games = new ArrayList<>();
    // Map each property to list of sessions that are subscribed to that property
    private static final Map<Integer, List<Session>> strategoGames = new HashMap<>();
    private DefaultFactory defaultFactory = new DefaultFactory();
    private PlaceUnitFactory placeUnitFactory = new PlaceUnitFactory();

    private int gameId = 0;
    private Gson gson = new Gson();

    @OnOpen
    public void onConnect(Session session) {
        System.out.println("[WebSocket Connected] SessionID: " + session.getId());

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

                    handleReady(session);
                    break;
                case UNREGISTERFROMGAME:
                    // Game done
                    unregisterFromGame(session);
                    break;
                case DELETE:
                    // Send game operation to client
                    handleDelete(session, gson, wbMessage);
                    break;

                case PLACEUNIT:
                    handlePlaceUnit(session, gson, wbMessage);
                    break;
                case MOVEMENT:

                    handleMovement(session, gson, wbMessage);
                    break;
                case PLACEALL:
                    handlePlaceAll(session, gson, wbMessage);
                    break;
                case REMOVEALL:
                    handleRemoveAll(session, gson, wbMessage);
                    break;

                default:
                    System.out.println("[WebSocket ERROR: cannot process Json message " + jsonMessage);
                    break;
            }
        }
    }

    private void handleRemoveAll(Session session, Gson gson, Message wbMessage) {
        RemoveAllUnitsMessage removeall = gson.fromJson(wbMessage.getResult(), new TypeToken<RemoveAllUnitsMessage>() {
        }.getType());
        int tempgameid = getKeyBasedOnSession(session);
        games.get(tempgameid).removeAllPieces(Integer.parseInt(session.getId()), removeall.getTeamcolor() == 1 ? Color.RED : Color.BLUE);
        sendMessageWithMessageTypeToBothUsersInGame(new Message(MessageType.RESETUI, gson.toJson(defaultFactory.CreateMessage(MessageType.RESETUI,removeall.getTeamcolor()))), tempgameid);
        //TODO Make this fully server side
    }

    private void handlePlaceAll(Session session, Gson gson, Message wbMessage) {
        PlaceAllUnitsMessage placeAllUnitsMessage = gson.fromJson(wbMessage.getResult(), new TypeToken<PlaceAllUnitsMessage>() {
        }.getType());

        games.get(getKeyBasedOnSession(session)).placePiecesAutomatically(Integer.parseInt(session.getId()), placeAllUnitsMessage.getTeamcolor() == 1 ? Color.RED : Color.BLUE);
    }

    private void handleMovement(Session session, Gson gson, Message wbMessage) {
        MovementMessage movementMessage = gson.fromJson(wbMessage.getResult(), new TypeToken<MovementMessage>() {
        }.getType());
        games.get(getKeyBasedOnSession(session)).movePiece(movementMessage.getOldCoords(), movementMessage.getNewCoords());
    }

    private void handlePlaceUnit(Session session, Gson gson, Message wbMessage) {
        PlaceUnitMessage placeunit = gson.fromJson(wbMessage.getResult(), new TypeToken<PlaceUnitMessage>() {
        }.getType());
        if (games.get(getKeyBasedOnSession(session)).placePiece(Integer.parseInt(session.getId()), Rank.valueOf(placeunit.getRank()), placeunit.getCoordsToPlace().x, placeunit.getCoordsToPlace().y, placeunit.getColor() == 1 ? Color.RED : Color.BLUE)) {
            sendOperationToBoth(session, wbMessage.getResult(), MessageType.PLACEUNITFOROPPONENT);
        }
        //TODO Make this fully serversided.
    }

    private void handleDelete(Session session, Gson gson, Message wbMessage) {
        if (wbMessage.getResult().equals("") || wbMessage.getResult() == (null)) return;
        DeleteMessage deleteMessage = gson.fromJson(wbMessage.getResult(), new TypeToken<DeleteMessage>() {
        }.getType());
        games.get(getKeyBasedOnSession(session)).removePiece(deleteMessage.getCoords());
        sendOperationToBoth(session, wbMessage.getResult(), MessageType.DELETE);
    }

    private void handleReady(Session session) {
        if (!isUserInGame(session)) {
            findGameForUser(session);
        } else {
            int gameid = getKeyBasedOnSession(session);
            if (games.get(gameid).haveBothPlayersPlacedAllUnits()) {
                games.get(gameid).startGamePlayingPhase();
                sendOperationToBoth(session, null, MessageType.STARTPLAYINGPHASE);
            } else {
                //send message to session yo both players haven't placed everything down boii.
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

   /* private void sendOperationToOpponent(Session session, String content, MessageType mt) {
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
    }*/

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

        for (Map.Entry<Integer, List<Session>> gameSession : strategoGames.entrySet()) {
            for (Session s : gameSession.getValue()) {
                if (s == session) {

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
                initGame(gameSession);
                return;
            }
        }
        //game not found, create new lobby
        gameId++;
        strategoGames.put(gameId, new ArrayList<>());
        strategoGames.get(gameId).add(session);
    }

    private void initGame(Map.Entry<Integer, List<Session>> gameSession) {


        games.add(new StrategoGame(gameSession.getKey() - 1, new CommunicatorServer(this), Integer.parseInt(gameSession.getValue().get(0).getId()), Integer.parseInt(gameSession.getValue().get(1).getId())));
        //TODO set id here


        games.get(gameSession.getKey() - 1).startGamePlanningPhase();
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

        for (Session s : gameSession.getValue()) {


            Message webSocketMessage = new Message(MessageType.MATCHFOUND, gson.toJson(sessionvalue));
            sessionvalue++;
            System.out.println("[WebSocket send ] " + webSocketMessage + " to:" + s.getId());
            System.out.println("[WebSocket send ] " + webSocketMessage + " to:" + s.getId());

            s.getAsyncRemote().sendText(gson.toJson(webSocketMessage));
        }


    }

    @Override
    public void moveUnit(Point oldPos, Point newPos, int gameId) {
        sendMessageWithMessageTypeToBothUsersInGame(new Message(MessageType.MOVEMENT, gson.toJson(new MovementMessage(oldPos, newPos))), gameId);

    }

    @Override
    public void deleteUnit(Point pos, int gameId) {

        sendMessageWithMessageTypeToBothUsersInGame(new Message(MessageType.DELETE, gson.toJson(new DeleteMessage(pos))), gameId);


    }

    private void sendMessageWithMessageTypeToBothUsersInGame(Message message, int gameId) {


        for (Session s : strategoGames.get(gameId+1)) {
            s.getAsyncRemote().sendText(gson.toJson(message));
        }


    }

    @Override
    public void endGame(Color teamcolor, int gameId) {
        int color = teamcolor == Color.RED ? 1 : 2;
        // unregisterFromGame();
        sendMessageWithMessageTypeToBothUsersInGame(new Message(MessageType.ENDGAME, gson.toJson(new EndGameMessage(color))), gameId);
    }

    @Override
    public void sendMessageNotYourTurn(String string, int playerId, int gameId) {


        sendMessageToUserInGameWithPlayerId(playerId, gameId, MessageType.NOTYOURTURN, null);
        // TODO Not Needed Message, fix by working with playerids.
    }

    private void sendMessageToUserInGameWithPlayerId(int playerId, int gameId, MessageType mt, String result) {
        try {
            getSessionBasedOnPlayerIdAndGameId(playerId, gameId).getBasicRemote().sendText(gson.toJson(new Message(mt, result)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {

        }
    }

    private Session getSessionBasedOnPlayerIdAndGameId(int playerId, int gameId) {
        return strategoGames.get(gameId+1).stream().filter(s -> Integer.parseInt(s.getId()) == playerId).findFirst().orElse(null);
    }

    @Override
    public void placeAllPieces(List<Piece> pieces, List<Point> points, Color color, int gameId) {
        int teamcolor = color == Color.RED ? 1 : 2;
        if (!pieces.isEmpty()) {
            sendMessageWithMessageTypeToBothUsersInGame(new Message(MessageType.RESETUI, gson.toJson(defaultFactory.CreateMessage(MessageType.RESETUI,teamcolor))), gameId);
        }
        while (!pieces.isEmpty()) {
            sendMessageWithMessageTypeToBothUsersInGame(new Message(MessageType.PLACEUNIT, gson.toJson(placeUnitFactory.CreateMessage(MessageType.PLACEUNIT,points.get(0), teamcolor, pieces.get(0).getActualRank().toString()))), gameId);
            pieces.remove(0);
            points.remove(0);
        }
    }

    @Override
    public void updateFrequencyUI(List<Rank> ranks, int gameId, int playerId) {
        ArrayList<String> ranksasstring = new ArrayList<>();

        for (Rank r : ranks) {
            ranksasstring.add(r.toString());
        }
        sendMessageToUserInGameWithPlayerId(playerId, gameId, MessageType.UPDATEFREQUENCY, gson.toJson(new UpdateFrequencyMessage(ranksasstring, playerId)));
        // TODO Not Needed Message, fix by working with playerids.
    }

    @Override
    public void logBattleResult(Rank attackRank, Rank defenderRank, boolean winsFight, int gameId) {
        sendMessageWithMessageTypeToBothUsersInGame(new Message(MessageType.BATTLERESULT, gson.toJson(new BattleResultMessage(attackRank.toString(), defenderRank.toString(), winsFight))), gameId);
    }
}
