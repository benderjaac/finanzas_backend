package com.primeng.primeng.repositories;

import com.primeng.primeng.models.AhorroDeposito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AhorroDepositoRepository extends JpaRepository<AhorroDeposito, Long> {
    Optional<AhorroDeposito> findByIdAndAhorroId(Long id, Long ahorroId);

    int deleteByIdAndAhorroId(Long id, Long ahorroId);
}
