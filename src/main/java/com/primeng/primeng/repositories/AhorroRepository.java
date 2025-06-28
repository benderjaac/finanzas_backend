package com.primeng.primeng.repositories;

import com.primeng.primeng.models.Ahorro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AhorroRepository extends JpaRepository<Ahorro, Long> {

    List<Ahorro> findByUsuarioId(Long usuarioId);
}
