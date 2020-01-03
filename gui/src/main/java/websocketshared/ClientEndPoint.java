package websocketshared;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import frontendenums.GameStatus;
import frontendenums.Rank;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import messageenum.MessageType;
import messages.*;

import javax.swing.*;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@ClientEndpoint
public class ClientEndPoint {

    private Session session;

    public ClientEndPoint(){
        try {

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this,new URI("ws://localhost:2222/stratego")); //TODO Change this to localhost or ip);
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @OnMessage
    public void message(String socketMessage){
        Gson gson = new Gson();
       Message message = gson.fromJson(socketMessage, Message.class);
        switch ((message.getMessageType())) {
            case DELETE:
                DeleteMessage deleteMessage = gson.fromJson(message.getResult(), new TypeToken<DeleteMessage>(){}.getType());
                WebSocketGui.getGameController().deleteUnitAtPosition(deleteMessage.getCoords());
                break;
            case MATCHFOUND:

                Integer teamcolor  = gson.fromJson(message.getResult(), new TypeToken<Integer>(){}.getType());
                WebSocketGui.getGameController().matchFound(teamcolor);
                WebSocketGui.getGameController().setGameStatus(GameStatus.SETUP);
                break;
            case MOVEMENT:
                MovementMessage movementMessage = gson.fromJson(message.getResult(), new TypeToken<MovementMessage>(){}.getType());
                WebSocketGui.getGameController().moveUnitToPosition(movementMessage.getOldCoords(),movementMessage.getNewCoords());
                break;
            case PLACEUNIT:
               PlaceUnitMessage unit = gson.fromJson(message.getResult(), new TypeToken<PlaceUnitMessage>(){}.getType());
                WebSocketGui.getGameController().placeUnitForOpponent(unit.getCoordsToPlace().x,unit.getCoordsToPlace().y);
                break;
            case STARTPLAYINGPHASE:
                WebSocketGui.getGameController().setGameStatus(GameStatus.PLAYING);

                break;
            case ENDGAME:
                EndGameMessage endGameMessage = gson.fromJson(message.getResult(), new TypeToken<EndGameMessage>(){}.getType());
                WebSocketGui.getGameController().endGame(endGameMessage.getTeamcolor());
                break;
            case NOTYOURTURN:
                NotYourTurnMessage notYourTurnMessage = gson.fromJson(message.getResult(), new TypeToken<NotYourTurnMessage>(){}.getType());

                WebSocketGui.getGameController().notYourTurn(notYourTurnMessage.getTeamcolor());
                break;


        }


    }

    @OnOpen
    public void open(Session session){
        this.session = session;
    }

    @OnError
    public void error(Throwable throwable){
        throwable.printStackTrace();
    }

    public void sendMessage(Message message){
        try {
            Gson gson = new Gson();
            String messageString = gson.toJson(message);
            session.getBasicRemote().sendText(messageString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}