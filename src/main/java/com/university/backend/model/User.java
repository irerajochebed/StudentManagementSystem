package com.university.backend.model;

public class User {
    private String username;
    private String password;
    private UserRole role;
    private String linkedId; // Student ID or Instructor ID

    public enum UserRole {
        ADMIN, INSTRUCTOR, STUDENT
    }

    public User(String username, String password, UserRole role, String linkedId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkedId = linkedId;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    
    public String getLinkedId() { return linkedId; }
    public void setLinkedId(String linkedId) { this.linkedId = linkedId; }
}
