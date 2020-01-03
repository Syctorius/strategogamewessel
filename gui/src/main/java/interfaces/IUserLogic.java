package interfaces;

import user.User;

public interface IUserLogic {
    boolean register(String username, String password);
    boolean login(String username, String password);
}
