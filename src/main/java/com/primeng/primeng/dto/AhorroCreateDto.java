package com.primeng.primeng.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class AhorroCreateDto {
    public LocalDate fecha_inicio;
    public String descri;
    public Float monto_meta;
    public Float monto_actual;
}
