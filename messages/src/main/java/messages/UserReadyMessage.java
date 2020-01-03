package messages;

import messageenum.MessageType;

public class UserReadyMessage extends Message {
    private boolean singleplayer;

    public UserReadyMessage(boolean singleplayer,MessageType mt) {
        this.singleplayer = singleplayer;
        this.mt =mt;
    }
    private MessageType mt;

    public boolean isSinglePlayer() {
        return singleplayer;
    }
}
