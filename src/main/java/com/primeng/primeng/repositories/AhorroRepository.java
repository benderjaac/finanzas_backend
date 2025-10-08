package com.primeng.primeng.repositories;

import com.primeng.primeng.models.Ahorro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AhorroRepository extends JpaRepository<Ahorro, Long> {

    List<Ahorro> findByUsuarioIdOrderById(Long usuarioId);

    Optional<Ahorro> findByIdAndUsuarioId(Long id, Long usuarioId);

    int deleteByIdAndUsuarioId(Long id, Long usuarioId);
}
