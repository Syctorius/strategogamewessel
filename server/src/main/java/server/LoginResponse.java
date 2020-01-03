package server;

import dtos.LoginDTO;

import java.util.List;

public class LoginResponse {

    // Indicates whether REST call was successful
    private boolean success;

    private List<LoginDTO> users;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<LoginDTO> getUsers() {
        return users;
    }

    public void setUsers(List<LoginDTO> users) {
        this.users = users;
    }
}
