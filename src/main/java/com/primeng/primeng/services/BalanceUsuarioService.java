package com.primeng.primeng.services;

import com.primeng.primeng.dto.BalanceUsuarioDto;
import com.primeng.primeng.models.BalanceUsuario;
import com.primeng.primeng.repositories.BalanceUsuarioRepository;
import com.primeng.primeng.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceUsuarioService {
    @Autowired
    private BalanceUsuarioRepository balanceUsuarioRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserService userService;

    public BalanceUsuarioDto getByIdUsuario(){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        BalanceUsuario balanceUsuario = balanceUsuarioRepository.findByUsuarioId(usuario.getId()).orElseThrow();

        return new BalanceUsuarioDto(balanceUsuario);
    }
}
