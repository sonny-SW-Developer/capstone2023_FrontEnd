package com.example.a23__project_1.request;

public class RegisterUserRequest {
    private String email;
    private String password;
    private String role;
    private String username;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public RegisterUserRequest(String email, String password, String role, String username) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.username = username;
    }
}
