package com.primeng.primeng.dto;

import com.primeng.primeng.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private UserDto user;

    public AuthResponse(String token, UserDto userdto) {
        this.token = token;
        this.user = userdto;
    }
}
