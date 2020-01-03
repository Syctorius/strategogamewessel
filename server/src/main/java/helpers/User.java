package helpers;

import server.LoginDTO;

public class User {
    public User()
    {

    }
    public User(int id, String username) {
        userId = id;
        this.username = username;
    }
    public User(int id, String username, String password) {
        userId = id;
        this.username = username;
        this.password = password;
    }

    private int userId;
    private String username;
    private Board board;
    private String password;
    private boolean ready;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setReady(){ready = true;}
    public boolean getReadyState(){return ready;}

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Board getBoard() {
        if(board!= null)
        {  return board;
        }
        this.board = new Board(10,10);
        return board;

    }

    public void setBoard(Board board) {
        this.board = board;
    }
    public LoginDTO createDTO () {
        return new LoginDTO(userId, username, password);
    }

}
