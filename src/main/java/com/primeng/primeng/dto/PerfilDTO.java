package com.primeng.primeng.dto;

import com.primeng.primeng.models.Perfil;

import java.util.List;
import java.util.stream.Collectors;

public class PerfilDTO {
    private Long id;
    private String nombre;
    private String descri;

    //private List<PermisoDTO> permisos;
    private List<PermisoDTO> menu;

    public PerfilDTO(Perfil perfil) {
        this.id = perfil.getId();
        this.nombre = perfil.getNombre();
        this.descri = perfil.getDescri();


        if (perfil.getMenu() != null) {
            System.out.println("PerfilDTO: Construyendo menu del perfil con " + perfil.getMenu().size() + " elementos");
            this.menu = perfil.getMenu().stream()
                    .map(PermisoDTO::new)
                    .collect(Collectors.toList());
        } else {
            System.out.println("PerfilDTO:: Menu nulo para perfil ID " + perfil.getId());
        }
    }

    public PerfilDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public List<PermisoDTO> getMenu() {
        return menu;
    }

    public void setMenu(List<PermisoDTO> menu) {
        this.menu = menu;
    }
}
