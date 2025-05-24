package com.primeng.primeng.dto;

import com.primeng.primeng.models.User;

public class AuthResponse {
    private String token;
    private UserDto user;

    public AuthResponse(String token, UserDto userdto) {
        this.token = token;
        this.user = userdto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
