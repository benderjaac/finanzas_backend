package com.primeng.primeng.dto;

import com.primeng.primeng.models.Gasto;
import com.primeng.primeng.models.Ingreso;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class IngresoDto {
    public Long id;
    public Boolean contado;
    public String descri;
    public Date fecha;
    public Float monto;
    private Long categoria_id;
    private String categoriaNombre;
    private String categoriaColor;
    private String categoriaIcon;

    public IngresoDto(Ingreso ingreso){
        this.id = ingreso.getId();
        this.contado = ingreso.getContado();
        this.descri = ingreso.getDescri();
        this.fecha = ingreso.getFecha();
        this.monto = ingreso.getMonto();
        CategoriaIngresoDto categoria = new CategoriaIngresoDto(ingreso.getCategoria());
        this.categoria_id = categoria.getId();
        this.categoriaNombre = categoria.getNombre();
        this.categoriaColor = categoria.getColor();
        this.categoriaIcon = categoria.getIcon();
    }
}
