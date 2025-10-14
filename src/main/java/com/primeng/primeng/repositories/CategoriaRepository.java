package com.primeng.primeng.repositories;

import com.primeng.primeng.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByUsuarioIdAndVisibleTrue(Long usuarioId);

    Optional<Categoria> findByIdAndUsuarioId(Long id, Long usuarioId);

    int deleteByIdAndUsuarioId(Long id, Long usuarioId);
}
