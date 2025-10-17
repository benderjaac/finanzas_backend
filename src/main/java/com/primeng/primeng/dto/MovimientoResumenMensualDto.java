package com.primeng.primeng.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MovimientoResumenMensualDto {
    private LocalDate mes;
    private Double totalIngresos;
    private Double totalGastos;

    public MovimientoResumenMensualDto(Object[] row) {
        this.mes = ((java.sql.Date) row[0]).toLocalDate();
        this.totalIngresos = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
        this.totalGastos = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;
    }
}
