package com.primeng.primeng.repositories;

import com.primeng.primeng.models.Gasto;
import com.primeng.primeng.models.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    Optional<Ingreso> findByIdAndUsuarioId(Long id, Long usuarioId);
}
