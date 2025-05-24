package com.primeng.primeng.dto;

import com.primeng.primeng.models.Perfil;
import com.primeng.primeng.models.User;

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

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PerfilDTO getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilDTO perfil) {
        this.perfil = perfil;
    }
}
