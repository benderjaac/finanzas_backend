package com.primeng.primeng.dto;

import com.primeng.primeng.models.Gasto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GastoDto {
    public Long id;
    public Boolean contado;
    public String descri;
    public LocalDate fecha;
    public Float monto;
    private Long categoria_id;
    private String categoriaNombre;
    private String categoriaColor;
    private String categoriaIcon;

    public GastoDto(Gasto gasto){
        this.id = gasto.getId();
        this.contado = gasto.getContado();
        this.descri = gasto.getDescri();
        this.fecha = gasto.getFecha();
        this.monto = gasto.getMonto();
        CategoriaGastoDto categoria = new CategoriaGastoDto(gasto.getCategoria());
        this.categoria_id = categoria.getId();
        this.categoriaNombre = categoria.getNombre();
        this.categoriaColor = categoria.getColor();
        this.categoriaIcon = categoria.getIcon();
    }

}
