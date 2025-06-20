package com.primeng.primeng.dto;

import com.primeng.primeng.models.CategoriaGasto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaGastoDto {
    public Long id;
    public String color;
    public String descri;
    public String icon;
    public String nombre;
    public Boolean visible;

    public CategoriaGastoDto(CategoriaGasto CG){
        this.id = CG.getId();
        this.color = CG.getColor();
        this.descri = CG.getDescri();
        this.icon = CG.getIcon();
        this.nombre = CG.getNombre();
        this.visible = CG.getVisible();
    }
}
