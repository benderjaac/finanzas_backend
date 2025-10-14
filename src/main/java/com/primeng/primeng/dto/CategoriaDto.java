package com.primeng.primeng.dto;

import com.primeng.primeng.models.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDto {
    public Long id;
    public String color;
    public String descri;
    public String tipo;
    public String icon;
    public String nombre;
    public Boolean visible;

    public CategoriaDto(Categoria CG){
        this.id = CG.getId();
        this.color = CG.getColor();
        this.descri = CG.getDescri();
        this.icon = CG.getIcon();
        this.nombre = CG.getNombre();
        this.tipo = CG.getTipo();
        this.visible = CG.getVisible();
    }
}
