package com.studentms.backend.auth;

import java.util.ArrayList;
import java.util.List;

public class UserStore {

    public static class User {
        private final String username;
        private final String password;
        private final String role;       // "ADMIN", "INSTRUCTOR", "STUDENT"
        private final String fullName;
        private final String linkedId;   // studentId or instructorId — blank for ADMIN

        public User(String username, String password, String role, String fullName, String linkedId) {
            this.username = username;
            this.password = password;
            this.role     = role;
            this.fullName = fullName;
            this.linkedId = linkedId;
        }

        public String getUsername()  { return username; }
        public String getPassword()  { return password; }
        public String getRole()      { return role; }
        public String getFullName()  { return fullName; }
        public String getLinkedId()  { return linkedId; }
    }

    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User("admin",      "admin123",   "ADMIN",      "System Admin", ""));
        users.add(new User("instructor", "teach123",   "INSTRUCTOR", "Dr. Smith",    "INS-001"));
        users.add(new User("student",    "student123", "STUDENT",    "John Doe",     "STU-001"));
    }

    public static User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) return u;
        }
        return null;
    }

    public static String register(String username, String password, String role,
                                  String fullName, String linkedId) {
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty())
            return "All fields are required.";
        if (password.length() < 6)
            return "Password must be at least 6 characters.";
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username))
                return "Username '" + username + "' is already taken.";
            if (!linkedId.isEmpty() && linkedId.equals(u.getLinkedId()))
                return "ID '" + linkedId + "' is already linked to another account.";
        }
        users.add(new User(username, password, role, fullName, linkedId));
        return null;
    }
}
