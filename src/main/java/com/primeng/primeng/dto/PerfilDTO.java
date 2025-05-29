package com.primeng.primeng.dto;

import com.primeng.primeng.models.Perfil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
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
            this.menu = perfil.getMenu().stream()
                    .map(PermisoDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public PerfilDTO() {
    }
}
