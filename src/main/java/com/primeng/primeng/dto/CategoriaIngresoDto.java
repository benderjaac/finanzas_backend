package com.primeng.primeng.dto;

import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.CategoriaIngreso;

public class CategoriaIngresoDto {
    public Long id;
    public String color;
    public String descri;
    public String icon;
    public String nombre;
    public Boolean visible;

    public CategoriaIngresoDto(CategoriaIngreso CI){
        this.id = CI.getId();
        this.color = CI.getColor();
        this.descri = CI.getDescri();
        this.icon = CI.getIcon();
        this.nombre = CI.getNombre();
        this.visible = CI.getVisible();
    }
}
