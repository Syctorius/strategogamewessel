package websocketshared;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import frontendenums.GameStatus;
import frontendenums.Rank;
import interfaces.ICommunicatorClient;
import messages.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@ClientEndpoint
public class ClientEndPoint {

    private Session session;
    private ICommunicatorClient client = new CommunicatorClient();
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
                client.deleteUnitAtPosition(deleteMessage.getCoords());
                break;
            case MATCHFOUND:

                Integer teamcolor  = gson.fromJson(message.getResult(), new TypeToken<Integer>(){}.getType());
                client.matchFound(teamcolor);
                client.setGameStatus(GameStatus.SETUP);
                break;
            case MOVEMENT:
                MovementMessage movementMessage = gson.fromJson(message.getResult(), new TypeToken<MovementMessage>(){}.getType());
                client.moveUnitToPosition(movementMessage.getOldCoords(),movementMessage.getNewCoords());
                break;
            case PLACEUNIT:
               PlaceUnitMessage unit = gson.fromJson(message.getResult(), new TypeToken<PlaceUnitMessage>(){}.getType());
                client.placeUnit(unit.getCoordsToPlace().x,unit.getCoordsToPlace().y,unit.getRank());
                break;
            case STARTPLAYINGPHASE:
                client.setGameStatus(GameStatus.PLAYING);
                client.disableButtons(true);

                break;
            case ENDGAME:
                EndGameMessage endGameMessage = gson.fromJson(message.getResult(), new TypeToken<EndGameMessage>(){}.getType());
                client.endGame(endGameMessage.getTeamcolor());
                break;
            case NOTYOURTURN:

                client.showNotYourTurn();
                break;
            case PLACEUNITFOROPPONENT:
                PlaceUnitMessage unitopponent = gson.fromJson(message.getResult(), new TypeToken<PlaceUnitMessage>(){}.getType());
                client.placeUnitForOpponent(unitopponent.getCoordsToPlace().x,unitopponent.getCoordsToPlace().y);
                break;
            case RESETUI:
                ResetUiMessage resetui = gson.fromJson(message.getResult(), new TypeToken<ResetUiMessage>(){}.getType());
                client.resetUI(resetui.getTeamcolor());
                break;
            case UPDATEFREQUENCY:
                UpdateFrequencyMessage updateFrequencyMessage  = gson.fromJson(message.getResult(), new TypeToken<UpdateFrequencyMessage>(){}.getType());
                ArrayList<Rank> ranks = new ArrayList<>();
                for (String s : updateFrequencyMessage.getRanks())
                {
                 ranks.add(Rank.valueOf(s));
                }
                client.updateFrequencyUI(ranks);
                break;
            case BATTLERESULT:
                BattleResultMessage battleResultMessage  = gson.fromJson(message.getResult(), new TypeToken<BattleResultMessage>(){}.getType());

                client.logBattleResult(battleResultMessage.getAttackerRank(),battleResultMessage.getDefenderRank(),battleResultMessage.doesAttackerWin());
                break;
            default:
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