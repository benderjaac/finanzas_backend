package com.primeng.primeng.repositories;

import com.primeng.primeng.models.BalanceHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BalanceHistoricoRepository extends JpaRepository<BalanceHistorico, Long> {

    List<BalanceHistorico> findBalanceHistoricosByUsuarioIdAndFechaGreaterThanEqualOrderByFechaAsc(
            Long usuarioId,
            LocalDate fecha
            );

    Optional<BalanceHistorico> findByUsuarioIdAndFecha(Long usuarioId,
                                                       LocalDate fecha);
}
