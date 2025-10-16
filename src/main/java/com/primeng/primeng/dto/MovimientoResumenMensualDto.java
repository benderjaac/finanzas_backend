package com.primeng.primeng.dto;

import java.time.LocalDate;

public interface MovimientoResumenMensualDto {
    LocalDate getMes();
    Double getTotalIngresos();
    Double getTotalGastos();
}
