package controllers;

import com.google.gson.Gson;
import frontendenums.GameStatus;
import frontendenums.MoveStatus;
import frontendenums.Rank;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import messageenum.MessageType;
import messages.Message;
import messages.MovementMessage;
import messages.PlaceUnitMessage;
import websocketshared.ClientEndPoint;
import websocketshared.WebSocketGui;

import java.awt.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.GridPane.getColumnIndex;
import static javafx.scene.layout.GridPane.getRowIndex;

public class StrategoControllerWessel implements Initializable {
    public Button Readybtn;
    public Button Removeunitbtn;
    public Button Placeunitbtn;
    public Button Generalbtn;
    public Button Majorbtn;
    public Button Lieutenantbtn;
    public Button Minerbtn;
    public Button Spybtn;
    public Button Colonelbtn;
    public Button Captainbutton;
    public Button Sergeantbtn;
    public Button Scoutbtn;
    public Button Marshalbtn;
    public Button bombbtn;
    public Button flagbtn;
    public TextArea Chatlog;
    @FXML
    GridPane Gridplayingfield = new GridPane();
    private Square toPlaceSquare = new Square();
    private Square[][] squareArray = new Square[10][10];
    private MoveStatus moveStatus = MoveStatus.NONE_SELECTED;
    private GameStatus gameStatus = GameStatus.STOPPED;
    private ClientEndPoint clientEndPoint;
    private boolean singleplayer = false;
    Rank r = Rank.SPY;
    private int teamColor = 1;
    Image SPY;
    Image BOMB;
    Image FLAG;
    Image MINER;
    Image SERGEANT;
    Image CAPTAIN;
    Image MARSHAL;
    Image GENERAL;
    Image COLONEL;
    Image MAJOR;
    Image LIEUTENANT;
    Image SCOUT;
    Image SPY1;
    Image BOMB1;
    Image FLAG1;
    Image MINER1;
    Image SERGEANT1;
    Image CAPTAIN1;
    Image MARSHAL1;
    Image GENERAL1;
    Image COLONEL1;
    Image MAJOR1;
    Image LIEUTENANT1;
    Image SCOUT1;
    Image SPY2;
    Image BOMB2;
    Image FLAG2;
    Image MINER2;
    Image SERGEANT2;
    Image CAPTAIN2;
    Image MARSHAL2;
    Image GENERAL2;
    Image COLONEL2;
    Image MAJOR2;
    Image LIEUTENANT2;
    Image SCOUT2;
    Image UNKNOWN1;
    Image UNKNOWN2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initBoard(squareArray, Gridplayingfield);
        initImages();
        disableButtons(true);
        WebSocketGui.setGameController(this);
        clientEndPoint = new ClientEndPoint();

    }

    private void disableButtons(boolean enabled) {
        Removeunitbtn.setDisable(enabled);
        Placeunitbtn.setDisable(enabled);
        Generalbtn.setDisable(enabled);
        Majorbtn.setDisable(enabled);
        Lieutenantbtn.setDisable(enabled);
        Minerbtn.setDisable(enabled);
        Spybtn.setDisable(enabled);
        Colonelbtn.setDisable(enabled);
        Captainbutton.setDisable(enabled);
        Sergeantbtn.setDisable(enabled);
        Scoutbtn.setDisable(enabled);
        Marshalbtn.setDisable(enabled);
        bombbtn.setDisable(enabled);
        flagbtn.setDisable(enabled);
    }

    private void initImages() {
        SPY = new Image("SPY.png");
        BOMB = new Image("BOMB.png");
        FLAG = new Image("FLAG.png");
        MINER = new Image("MINER.png");
        SERGEANT = new Image("SERGEANT.png");
        CAPTAIN = new Image("CAPTAIN.png");
        MARSHAL = new Image("MARSHAL.png");
        GENERAL = new Image("GENERAL.png");
        COLONEL = new Image("COLONEL.png");
        MAJOR = new Image("MAJOR.png");
        LIEUTENANT = new Image("LIEUTENANT.png");
        SCOUT = new Image("SCOUT.png");
        SPY1 = new Image("SPY1.png");
        BOMB1 = new Image("BOMB1.png");
        FLAG1 = new Image("FLAG1.png");
        MINER1 = new Image("MINER1.png");
        SERGEANT1 = new Image("SERGEANT1.png");
        CAPTAIN1 = new Image("CAPTAIN1.png");
        MARSHAL1 = new Image("MARSHAL1.png");
        GENERAL1 = new Image("GENERAL1.png");
        COLONEL1 = new Image("COLONEL1.png");
        MAJOR1 = new Image("MAJOR1.png");
        LIEUTENANT1 = new Image("LIEUTENANT1.png");
        SCOUT1 = new Image("SCOUT1.png");
        SPY2 = new Image("SPY2.png");
        BOMB2 = new Image("BOMB2.png");
        FLAG2 = new Image("FLAG2.png");
        MINER2 = new Image("MINER2.png");
        SERGEANT2 = new Image("SERGEANT2.png");
        CAPTAIN2 = new Image("CAPTAIN2.png");
        MARSHAL2 = new Image("MARSHAL2.png");
        GENERAL2 = new Image("GENERAL2.png");
        COLONEL2 = new Image("COLONEL2.png");
        MAJOR2 = new Image("MAJOR2.png");
        LIEUTENANT2 = new Image("LIEUTENANT2.png");
        SCOUT2 = new Image("SCOUT2.png");
        UNKNOWN1 = new Image("UNKNOWN1.png");
        UNKNOWN2 = new Image("UNKNOWN2.png");
        //=new Image("flower.png");
    }

    public void moveUnitToPosition(Point oldCoords, Point newCoords) {

        Square oldpos = (Square) getNodeByRowColumnIndex(oldCoords.x, oldCoords.y, Gridplayingfield);
        oldpos.unit = false;
        Square newpos = (Square) getNodeByRowColumnIndex(newCoords.x, newCoords.y, Gridplayingfield);
        newpos.unit =true;
        oldpos.setStyle("-fx-background-color:" + oldpos.color);
        newpos.ImageView.setImage(oldpos.ImageView.getImage());
        newpos.ImageView.setFitWidth(65.6);
        newpos.ImageView.setFitHeight(64.0); // make constant
        oldpos.setStyle("-fx-border-color: black; -fx-background-color: lime");
        newpos.ImageView.setStyle("-fx-border-color: black");
        //animation
        oldpos.ImageView.setImage(null);
    }

    public void matchFound(Integer teamcolor) {
        this.teamColor = teamcolor;
        disableButtons(false);

        showMessage("A match has been found.");
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        showMessage("You are now in the"+ gameStatus.name() +"Phase");
    }

    private void showMessage(final String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Information");
            alert.setContentText(message);
            alert.showAndWait();

        });
    }
    public void notYourTurn(Integer teamcolor3)
    {
        if( teamColor == teamcolor3) {
            showMessage("It's not your turn");
        }
    }

    public void deleteUnitAtPosition(Point oldCoords) {
        Square oldpos = (Square) getNodeByRowColumnIndex(oldCoords.x, oldCoords.y, Gridplayingfield);
        oldpos.unit = false;
        oldpos.setStyle("-fx-background-color:" + oldpos.color);
        oldpos.setStyle("-fx-border-color: black; -fx-background-color: lime");
        oldpos.ImageView.setImage(null);
    }

    private void setNodeColor(String s, Node nodeByRowColumnIndex) {
        nodeByRowColumnIndex.setStyle("-fx-background-color:+ s;+ -fx-border-color: black");
    }

    public void endGame(Integer teamcolor2) {
        showMessage(teamcolor2 == 1 ? "The red player has captured the blue flag, Game Over" : "The blue player has captured the red flag, Game Over");
        //Close application redirect or something

    }


    private class Square extends StackPane {

        public javafx.scene.image.ImageView ImageView;
        boolean land = true;
        int teamcolor;
        Node node;
        boolean unit = false;
        String color = "lime";

    }


    private void initBoard(Square[][] grid, GridPane board) {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 10; x++) {
                init(grid, board, y, x, 2);

            }
        }

        for (int y = 4; y < 6; y++) {
            for (int x = 0; x < 10; x++) {
                init(grid, board, y, x, 0);
            }

        }
        for (int y = 6; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                init(grid, board, y, x, 1);

            }
        }
    }

    private void init(Square[][] grid, GridPane board, int y, int x, int i) {
        Square s = new Square();
        ImageView image = new ImageView();

        s.ImageView = image;
        s.getChildren().add(image);
        s.setStyle("-fx-background-color: lime;  -fx-border-color: black");
        s.addEventHandler(MouseEvent.MOUSE_PRESSED,
                this::handleOnMouseClicked);
        s.teamcolor = i;
        grid[x][y] = s;


        board.add(grid[x][y], x, y);
    }

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {

        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null &&
                    GridPane.getRowIndex(node) == column && GridPane.getColumnIndex(node) == row) {
                return node;
            }
        }

        return null;
    }

    private void animationPlay(Node piece, Node newCoords) {
        Square square = (Square) piece;
        ImageView imageView = square.ImageView;
        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), imageView);
        imageView.toFront();

        animation.setFromY(imageView.getLayoutY());
        animation.setFromX(imageView.getLayoutX());

        animation.setToX(newCoords.getLayoutX());
        animation.setToY(newCoords.getLayoutY());
        animation.play();
    }


    @FXML
    private void handleOnMouseClicked(MouseEvent event) {
        Node source = (Node) event.getSource();
        Square square = (Square) source;

        switch (gameStatus) {
            case PLAYING:
                Square Tempsquare = toPlaceSquare;
                selectToPlaceSquare(source);
                if (moveStatus == moveStatus.PIECE_SELECTED) {
                    Gson gson = new Gson();
                    //Send message to server move;
                    clientEndPoint.sendMessage((new Message(MessageType.MOVEMENT, gson.toJson(new MovementMessage(new Point(GridPane.getColumnIndex(Tempsquare), GridPane.getRowIndex(Tempsquare)),new Point(GridPane.getColumnIndex(toPlaceSquare), GridPane.getRowIndex(toPlaceSquare)))))));

                    moveStatus = moveStatus.NONE_SELECTED;

                } else if (square.unit) {
                    moveStatus = moveStatus.PIECE_SELECTED;
                }
                break;
            case SETUP:
                selectToPlaceSquare(source);
                break;
            case STOPPED:
                break;
        }


    }

    private boolean canUnitBePlaced(double x, double y, int teamcolor) {
        if (isUnitInBounds(x, y, teamcolor)) {
            if (isUnitOnLandTile(squareArray[(int) x][(int) y])) return true;
        }
        return false;
    }

    private boolean isUnitOnLandTile(Square square) {
        if (square.land == true) {
            return true;
        }
        return false;
    }

    private boolean isUnitInBounds(double x, double y, int teamcolor) {
        return (x >= 0 && x <= squareArray[0].length && y >= 0 && y <= squareArray[1].length && squareArray[(int) x][(int) y].teamcolor == teamcolor);
    }

    @FXML
    private void PlaceS() {
        Rank r = Rank.SPY;
        tryToPlaceUnitAndSendMessage(r);

    }

    private void tryToPlaceUnitAndSendMessage(Rank r) {
        if (toPlaceSquare != null) {

            if (placeUnit(r)) {
                Gson gson = new Gson();
                clientEndPoint.sendMessage(new Message(MessageType.PLACEUNIT, gson.toJson(new PlaceUnitMessage(new Point(GridPane.getColumnIndex(toPlaceSquare), GridPane.getRowIndex(toPlaceSquare)), teamColor, r.toString()))));
            }
        }
    }

    @FXML
    public void Place3(MouseEvent mouseEvent) {
        Rank r = Rank.MINER;
        tryToPlaceUnitAndSendMessage(r);
    }

    @FXML
    public void Place2(MouseEvent mouseEvent) {
        Rank r = Rank.SCOUT;
        tryToPlaceUnitAndSendMessage(r);
    }

    @FXML
    public void Place4(MouseEvent mouseEvent) {
        Rank r = Rank.SERGEANT;
        tryToPlaceUnitAndSendMessage(r);
    }

    public void Place5(MouseEvent mouseEvent) {
        Rank r = Rank.LIEUTENANT;
        tryToPlaceUnitAndSendMessage(r);
    }

    @FXML
    public void Place6(MouseEvent mouseEvent) {
        Rank r = Rank.CAPTAIN;
        tryToPlaceUnitAndSendMessage(r);
    }

    @FXML
    public void Place7(MouseEvent mouseEvent) {
        Rank r = Rank.MAJOR;
        tryToPlaceUnitAndSendMessage(r);
    }

    @FXML
    public void Place8(MouseEvent mouseEvent) {
        Rank r = Rank.COLONEL;
        tryToPlaceUnitAndSendMessage(r);
    }

    @FXML
    public void Place9(MouseEvent mouseEvent) {
        Rank r = Rank.GENERAL;
        tryToPlaceUnitAndSendMessage(r);
    }

    @FXML
    public void PlaceX(MouseEvent mouseEvent) {
        Rank r = Rank.MARSHAL;
        tryToPlaceUnitAndSendMessage(r);
    }

    @FXML
    public void readyUp(MouseEvent mouseEvent) {
        Gson gson = new Gson();
        if (this.gameStatus == GameStatus.STOPPED) {
            clientEndPoint.sendMessage(new Message(MessageType.USERREADY, gson.toJson(singleplayer)));
        }
        if (this.gameStatus == GameStatus.SETUP) {
            clientEndPoint.sendMessage(new Message(MessageType.USERREADY, gson.toJson(singleplayer)));
        }
        //add singleplayer
    }

    @FXML
    public void PlaceBomb(MouseEvent mouseEvent) {
        Rank r = Rank.BOMB;
        tryToPlaceUnitAndSendMessage(r);
    }

    @FXML
    public void PlaceFlag(MouseEvent mouseEvent) {
        Rank r = Rank.FLAG;
        tryToPlaceUnitAndSendMessage(r);
    }

    public boolean placeUnit(Rank rank) {


        if (canUnitBePlaced(getColumnIndex(toPlaceSquare), getRowIndex(toPlaceSquare), teamColor)) {


            toPlaceSquare.ImageView.setImage(getImage(rank.toString() + teamColor));
            setSquareImageForPlacing(toPlaceSquare);
            return true;
        } else {
            showMessage("You can't place your unit here (Enemy Terrain)");
            return false;
        }
    }

    private void setSquareImageForPlacing(Square s) {
        s.ImageView.setFitWidth(65.6);
        s.ImageView.setFitHeight(64.0);
        s.unit = true;
        s.node = null;
    }

    public void placeUnitForOpponent(int x, int y) {
        int opponentTeamColor = teamColor == 1 ? 2 : 1; // can remove

        if (canUnitBePlaced(x, y, opponentTeamColor)) {
            Square placeLocation = (Square) getNodeByRowColumnIndex(x, y, Gridplayingfield);
            placeLocation.ImageView.setImage(getImage("UNKNOWN" + opponentTeamColor));
            setSquareImageForPlacing(placeLocation);
        }
    }

    private Image getImage(String string) {
        try {
            final Field declaredField = this.getClass()
                    .getDeclaredField(string);
            final Object o = declaredField.get(this);
            if (o instanceof Image) {
                return (Image) o;
            }

        } catch (final Exception e) {
        }
        return null;
    }

    private void selectToPlaceSquare(Node source) {

        if (toPlaceSquare.node != null) {

            toPlaceSquare.setStyle("-fx-background-color:" + toPlaceSquare.color + "; -fx-border-color: black");

            toPlaceSquare = new Square();
        }

        source.setStyle("-fx-background-color: yellow; -fx-border-color: black");
        toPlaceSquare = (Square) source;

        toPlaceSquare.node = source;
    }
}
