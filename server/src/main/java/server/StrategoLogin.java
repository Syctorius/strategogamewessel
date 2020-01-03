package server;

import helpers.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategoLogin {
    // The users are stored in a map with key userId and value User-object
    private final Map<Integer, User> users;

    // Id for the next user to be added to the list
    private int nextUserId;

    // Instance of user list (singleton pattern)
    private static StrategoLogin instance;

    // Singleton pattern
    StrategoLogin() {
        // Initially, there are no users in the user list
        users = new HashMap<>();
        nextUserId = 0;
    }

    /**
     * Get singleton instance of user store.
     */
    public static  StrategoLogin getInstance() {
        if (instance == null) {
            instance = new  StrategoLogin();
        }
        return instance;
    }


    public User getUser(int userId) {
        return users.get(userId);
    }


    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    public User getUser(String userName) {
        User user = new User();
        for (User u : users.values()) {

            if (u.getUsername().equals(userName)) {
                user = u;
            }

        }
        return user;
    }



    public User addUser(String userName, String password) {
        // Define id for the new user
        int userId = nextUserId;
        nextUserId++;

        // Create the new user
        User user = new User(userId, userName, password);

        // Put the new user in the user "map"
        users.put(userId,user);

        // Return the new user
        return user;
    }

    public boolean removeUser(int userId) {
        User userRemoved = users.remove(userId);
        if (userRemoved != null) {
            // User successfully removed
            return true;
        }
        // User not found
        return false;
    }

}
