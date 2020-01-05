package messages;

import messageenum.MessageType;

public class UserReadyMessage extends Message {
    private boolean singleplayer;
    private String username;

    public UserReadyMessage(boolean singleplayer,MessageType mt) {
        this.singleplayer = singleplayer;
        this.mt =mt;
    }
    public UserReadyMessage(boolean singleplayer,MessageType mt, String username) {
        this.singleplayer = singleplayer;
        this.mt =mt;
        this.username = username;
    }
    private MessageType mt;

    public boolean isSinglePlayer() {
        return singleplayer;
    }

    public String getUsername() {
        return username;
    }
}
