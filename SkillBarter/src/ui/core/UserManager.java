package ui.core;

import ui.models.User;

/**
 * User manager with API integration
 */
public class UserManager {
    private User currentUser;
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public Long getCurrentUserId() {
        return currentUser != null ? currentUser.getId() : null;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public void refreshUser() {
        if (currentUser != null && currentUser.getId() != null) {
            try {
                User updated = APIClient.getUserById(currentUser.getId());
                if (updated != null) {
                    currentUser = updated;
                }
            } catch (Exception e) {
                // Silently fail - user data will be stale
            }
        }
    }
}
