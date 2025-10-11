package com.primeng.primeng.dto;

import com.primeng.primeng.models.AhorroDeposito;
import com.primeng.primeng.models.BalanceUsuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter

public class BalanceUsuarioDto {
    public Long id;
    public LocalDateTime ultimaActualizacion;
    public Float montoDisponible;
    public Float montoAhorrado;
    public Float balanceTotal;

    public BalanceUsuarioDto(BalanceUsuario balanceUsuario){
        this.id = balanceUsuario.getId();
        this.ultimaActualizacion = balanceUsuario.getUltimaActualizacion();
        this.montoDisponible = balanceUsuario.getMontoDisponible();
        this.montoAhorrado = balanceUsuario.getMontoAhorrado();
        this.balanceTotal = balanceUsuario.getBalanceTotal();
    }
}
