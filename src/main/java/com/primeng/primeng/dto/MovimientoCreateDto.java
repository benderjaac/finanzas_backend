package com.primeng.primeng.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class MovimientoCreateDto {
    public String descri;
    public String tipo;
    public LocalDate fecha;
    public Float monto;
    private Long categoriaId;
}
