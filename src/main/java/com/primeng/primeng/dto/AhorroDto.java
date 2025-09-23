package com.primeng.primeng.dto;

import com.primeng.primeng.models.Ahorro;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class AhorroDto {
    public Long id;
    public LocalDate fecha_inicio;
    public String descri;
    public Float monto_meta;
    public Float monto_actual;

    public AhorroDto(Ahorro ahorro){
        this.id = ahorro.getId();
        this.fecha_inicio = ahorro.getFecha_inicio();
        this.descri = ahorro.getDescri();
        this.monto_meta = ahorro.getMonto_meta();
        this.monto_actual = ahorro.getMonto_actual();
    }
}
