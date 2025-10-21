package com.primeng.primeng.repositories;

import com.primeng.primeng.models.BalanceUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceUsuarioRepository extends JpaRepository<BalanceUsuario, Long> {

    Optional<BalanceUsuario> findByUsuarioId(Long usuarioId);

}
