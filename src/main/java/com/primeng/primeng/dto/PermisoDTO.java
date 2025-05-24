package com.primeng.primeng.dto;

import com.primeng.primeng.models.Permiso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

            System.out.println("PermisoDTO: Permiso " + this.nombre + " tiene hijos: " + permiso.getHijos().size());

            this.hijos = permiso.getHijos().stream()
                    .map(PermisoDTO::new)
                    .collect(Collectors.toList());
        }

    }

    public PermisoDTO() {
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public Long getPadre_id() {
        return padre_id;
    }

    public void setPadre_id(Long padre_id) {
        this.padre_id = padre_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<PermisoDTO> getHijos() {
        return hijos;
    }

    public void setHijos(List<PermisoDTO> hijos) {
        this.hijos = hijos;
    }
}
