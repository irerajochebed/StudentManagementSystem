package com.university.backend.manager;

import com.university.backend.model.User;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {
    private Map<String, User> users;
    private User currentUser;

    public AuthenticationManager() {
        this.users = new HashMap<>();
        // Create default admin account
        users.put("admin", new User("admin", "admin123", User.UserRole.ADMIN, null));
    }

    public boolean register(String username, String password, User.UserRole role, String linkedId) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password, role, linkedId));
        return true;
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
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

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public Map<String, User> getAllUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
