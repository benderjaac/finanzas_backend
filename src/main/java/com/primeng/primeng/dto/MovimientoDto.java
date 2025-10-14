package com.primeng.primeng.dto;

import com.primeng.primeng.models.Movimiento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovimientoDto {
    public Long id;
    public String descri;
    public String tipo;
    public LocalDate fecha;
    public Float monto;
    private Long categoria_id;
    private String categoriaNombre;
    private String categoriaColor;
    private String categoriaIcon;

    public MovimientoDto(Movimiento movimiento){
        this.id = movimiento.getId();
        this.descri = movimiento.getDescri();
        this.tipo = movimiento.getTipo();
        this.fecha = movimiento.getFecha();
        this.monto = movimiento.getMonto();
        CategoriaDto categoria = new CategoriaDto(movimiento.getCategoria());
        this.categoria_id = categoria.getId();
        this.categoriaNombre = categoria.getNombre();
        this.categoriaColor = categoria.getColor();
        this.categoriaIcon = categoria.getIcon();
    }

}
