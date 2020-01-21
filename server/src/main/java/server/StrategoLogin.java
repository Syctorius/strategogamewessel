package server;

import datastorage.Persistence;
import user.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategoLogin {
    // The users are stored in a map with key userId and value User-object
    private Map<Integer, User> users;

    // Id for the next user to be added to the list
    private int nextUserId;
    private Persistence data = new Persistence();

    // Instance of user list (singleton pattern)
    private      static StrategoLogin instance;

    // Singleton pattern
    private StrategoLogin() {
        Map<Integer, User> users1;
        // Initially, there are no users in the user list
        users1 = new HashMap<>();

        try {
            users1 = data.getSavedData();
        } catch (java.io.EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        users = users1;
        nextUserId = 0;
    }

    /**
     * Get singleton instance of user store.
     */
    public static StrategoLogin getInstance() {
        if (instance == null) {
            instance = new StrategoLogin();
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
        User user = new User(userName, password);

        // Put the new user in the user "map"
        users.put(userId, user);

        // Return the new user
        try {
            data.saveData(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean removeUser(int userId) {
        User userRemoved = users.remove(userId);
        // User successfully removed
        return userRemoved != null;
        // User not found
    }


    public void useMockData() {
        users = new HashMap<>();
    }
}
