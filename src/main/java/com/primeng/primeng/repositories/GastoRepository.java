package com.primeng.primeng.repositories;

import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.Gasto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    @EntityGraph(attributePaths = "usuario")
    List<Gasto> findAll();
}
