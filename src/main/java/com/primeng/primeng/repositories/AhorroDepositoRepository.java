package com.primeng.primeng.repositories;

import com.primeng.primeng.models.AhorroDeposito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AhorroDepositoRepository extends JpaRepository<AhorroDeposito, Long> {
    List<AhorroDeposito> findByAhorroIdAndAhorroUsuarioId(Long ahorroId, Long usuarioId);

    Optional<AhorroDeposito> findByIdAndAhorroIdAndAhorroUsuarioId(Long id, Long ahorroId, Long usuarioId);

    int deleteByIdAndAhorroId(Long id, Long ahorroId);
}
