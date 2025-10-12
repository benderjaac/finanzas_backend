package com.primeng.primeng.repositories;

import com.primeng.primeng.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    Optional<Movimiento> findByIdAndUsuarioId(Long id, Long usuarioId);

    int deleteByIdAndUsuarioId(Long id, Long usuarioId);
}
