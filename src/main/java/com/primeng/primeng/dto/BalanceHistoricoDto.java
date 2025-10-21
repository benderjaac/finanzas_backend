package com.primeng.primeng.dto;

import com.primeng.primeng.models.BalanceHistorico;
import com.primeng.primeng.models.BalanceUsuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BalanceHistoricoDto {

    public Long id;
    public LocalDate fecha;
    public Float montoDisponible;
    public Float montoAhorrado;
    public Float balanceTotal;

    public BalanceHistoricoDto(BalanceHistorico balanceHistorico){
        this.id = balanceHistorico.getId();
        this.fecha = balanceHistorico.getFecha();
        this.montoDisponible = balanceHistorico.getMontoDisponible();
        this.montoAhorrado = balanceHistorico.getMontoAhorrado();
        this.balanceTotal = balanceHistorico.getMontoTotal();
    }
}
