package com.primeng.primeng.repositories;

import com.primeng.primeng.models.CategoriaGasto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {

    List<CategoriaGasto> findByUsuarioIdAndVisibleTrue(Long usuarioId);

    Optional<CategoriaGasto> findByIdAndUsuarioId(Long id, Long usuarioId);

    int deleteByIdAndUsuarioId(Long id, Long usuarioId);
}
