package user;

import dtos.LoginDTO;

import java.io.Serializable;


public class User implements Serializable {
    public User()
    {

    }


    private String username;
    private String password;
    private boolean ready;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
    public void setPassword(String password){this.password = password;}

    public LoginDTO createDTO () {
        return new LoginDTO(username, password);
    }

}
