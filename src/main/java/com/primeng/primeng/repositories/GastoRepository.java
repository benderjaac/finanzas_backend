package com.primeng.primeng.repositories;

import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface GastoRepository extends JpaRepository<Gasto, Long> {
    Optional<Gasto> findByIdAndUsuarioId(Long id, Long usuarioId);

    int deleteByIdAndUsuarioId(Long id, Long usuarioId);
}
