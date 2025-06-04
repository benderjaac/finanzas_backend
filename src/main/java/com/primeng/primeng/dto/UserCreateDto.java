package com.primeng.primeng.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
    private String username;
    private String email;
    private Long perfilId;
}
