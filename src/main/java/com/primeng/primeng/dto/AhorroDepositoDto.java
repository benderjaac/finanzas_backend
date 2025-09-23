package com.primeng.primeng.dto;

import com.primeng.primeng.models.AhorroDeposito;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class AhorroDepositoDto {
    public Long id;
    public LocalDate fecha;
    public String descri;
    public Float monto;

    public AhorroDepositoDto(AhorroDeposito ahorroDeposito){
        this.id = ahorroDeposito.getId();
        this.fecha = ahorroDeposito.getFecha();
        this.descri = ahorroDeposito.getDescri();
        this.monto = ahorroDeposito.getMonto();
    }
}
