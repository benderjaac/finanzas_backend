package com.primeng.primeng.dto;

import com.primeng.primeng.models.User;

public class UserSimpleDto {
    private Long id;
    private String username;
    private String email;
    private Long perfil_id;
    private String perfilNombre;


    public UserSimpleDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.perfil_id=user.getPerfil().getId();
        this.perfilNombre = user.getPerfil() != null ? user.getPerfil().getNombre() : null;
    }

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

    public String getPerfilNombre() {
        return perfilNombre;
    }

    public void setPerfilNombre(String perfilNombre) {
        this.perfilNombre = perfilNombre;
    }
}
