package com.primeng.primeng.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class GastoCreateDto {
    public String descri;
    public LocalDate fecha;
    public Float monto;
    private Long categoriaId;
}
