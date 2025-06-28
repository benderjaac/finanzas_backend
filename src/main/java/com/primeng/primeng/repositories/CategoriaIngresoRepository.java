package com.primeng.primeng.repositories;

import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.CategoriaIngreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaIngresoRepository extends JpaRepository<CategoriaIngreso, Long> {

    List<CategoriaIngreso> findByUsuarioIdAndVisibleTrue(Long usuarioId);

    Optional<CategoriaIngreso> findByIdAndUsuarioId(Long id, Long usuarioId);
}
