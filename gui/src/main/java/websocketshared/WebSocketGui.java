package websocketshared;

import controllers.StrategoControllerWessel;

public class WebSocketGui {
    private static StrategoControllerWessel gameController = null;

    public static StrategoControllerWessel getGameController() {
        return gameController;
    }

    public static void setGameController(StrategoControllerWessel gameController) {
        WebSocketGui.gameController = gameController;
    }
}
