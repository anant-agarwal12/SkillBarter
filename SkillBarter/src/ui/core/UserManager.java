package ui.core;

import ui.models.User;

/**
 * Simple user manager
 */
public class UserManager {
    private User currentUser;
    private long nextUserId = 1;
    
    public User register(String username, String password, String email) {
        User user = new User(nextUserId++, username, password, email);
        currentUser = user;
        return user;
    }
    
    public boolean login(String username, String password) {
        // Simplified - in real app, check against database
        if (currentUser != null && currentUser.getUsername().equals(username)) {
            return true;
        }
        return false;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public Long getCurrentUserId() {
        return currentUser != null ? currentUser.getId() : null;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public void logout() {
        currentUser = null;
    }
}
