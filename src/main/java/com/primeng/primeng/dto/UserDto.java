package com.primeng.primeng.dto;

import com.primeng.primeng.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private PerfilDTO perfil;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        if (user.getPerfil() != null) {
            this.perfil = new PerfilDTO(user.getPerfil());
        }
    }

    public UserDto() {
    }
}
