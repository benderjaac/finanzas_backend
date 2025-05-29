package com.primeng.primeng.dto;

import com.primeng.primeng.models.Permiso;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PermisoDTO {
    private Long id;
    private String nombre;
    private String rol;
    private boolean visible;
    private String descri;
    private Long padre_id;
    private String link;
    private String icon;
    private List<PermisoDTO> hijos = new ArrayList<>();

    public PermisoDTO(Permiso permiso) {
        this.id = permiso.getId();
        this.nombre = permiso.getNombre();
        this.rol = permiso.getRol();
        this.visible = permiso.isVisible();
        this.descri = permiso.getDescri();
        if(permiso.getPadre()!=null){
            this.padre_id = permiso.getPadre().getId();
        }

        this.link = permiso.getLink();
        this.icon = permiso.getIcon();
        // Recursivamente mapear los hijos
        if (permiso.getHijos() != null && !permiso.getHijos().isEmpty()) {

            this.hijos = permiso.getHijos().stream()
                    .map(PermisoDTO::new)
                    .collect(Collectors.toList());
        }

    }

    public PermisoDTO() {
    }
}
