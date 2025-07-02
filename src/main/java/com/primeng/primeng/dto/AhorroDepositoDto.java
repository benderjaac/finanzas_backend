package com.primeng.primeng.dto;

import com.primeng.primeng.models.Ahorro;
import com.primeng.primeng.models.AhorroDeposito;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class AhorroDepositoDto {
    public Long id;
    public Date fecha;
    public String descri;
    public Float monto;

    public AhorroDepositoDto(AhorroDeposito ahorroDeposito){
        this.id = ahorroDeposito.getId();
        this.fecha = ahorroDeposito.getFecha();
        this.descri = ahorroDeposito.getDescri();
        this.monto = ahorroDeposito.getMonto();
    }
}
