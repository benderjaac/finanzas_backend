package com.primeng.primeng.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class GastoCreateDTO {
    public String descri;
    public Date fecha;
    public Float monto;
    private Long categoriaId;
}
