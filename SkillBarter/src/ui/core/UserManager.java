package ui.core;

import ui.models.User;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private final List<User> users = new ArrayList<>();
    private User currentUser = null;

    public boolean register(String username, String password, String email) {
        if (getUserByUsername(username) != null) return false; // already exists
        users.add(new User(username, password, email));
        return true;
    }

    public boolean login(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) return u;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }
}
