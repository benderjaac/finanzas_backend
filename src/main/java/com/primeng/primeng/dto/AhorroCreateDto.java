package com.primeng.primeng.dto;

import com.primeng.primeng.models.Ahorro;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter

public class AhorroCreateDto {
    public Date fecha_inicio;
    public String descri;
    public Float monto_meta;
    public Float monto_actual;
}
