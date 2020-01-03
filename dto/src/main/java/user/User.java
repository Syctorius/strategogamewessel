package user;

import dtos.LoginDTO;

public class User  {
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
    private String password;
    private boolean ready;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    public LoginDTO createDTO () {
        return new LoginDTO(userId, username, password);
    }

}
