package com.primeng.primeng.repositories;

import com.primeng.primeng.models.CategoriaGasto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {
}
