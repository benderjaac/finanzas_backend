package com.primeng.primeng.dto;

public class AuthRequest {

    private String username;
    private String password;

    //Getters y Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
