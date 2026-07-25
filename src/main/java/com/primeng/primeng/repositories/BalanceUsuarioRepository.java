package com.primeng.primeng.repositories;

import com.primeng.primeng.models.BalanceUsuario;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceUsuarioRepository extends JpaRepository<BalanceUsuario, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BalanceUsuario> findByUsuarioId(Long usuarioId);

}
