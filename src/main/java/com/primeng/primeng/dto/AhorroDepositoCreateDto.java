package com.primeng.primeng.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class AhorroDepositoCreateDto {
    public LocalDate fecha;
    public String descri;
    public Float monto;
}
