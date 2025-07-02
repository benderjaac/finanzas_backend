package com.primeng.primeng.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter

public class AhorroDepositoCreateDto {
    public Date fecha;
    public String descri;
    public Float monto;
}
