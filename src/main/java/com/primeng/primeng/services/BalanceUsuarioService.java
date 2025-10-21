package com.primeng.primeng.services;

import com.primeng.primeng.dto.BalanceHistoricoDto;
import com.primeng.primeng.dto.BalanceUsuarioDto;
import com.primeng.primeng.dto.FechaDto;
import com.primeng.primeng.models.BalanceHistorico;
import com.primeng.primeng.models.BalanceUsuario;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Catalogo;
import com.primeng.primeng.repositories.BalanceHistoricoRepository;
import com.primeng.primeng.repositories.BalanceUsuarioRepository;
import com.primeng.primeng.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BalanceUsuarioService {
    @Autowired
    private BalanceUsuarioRepository balanceUsuarioRepository;

    @Autowired
    private BalanceHistoricoRepository balanceHistoricoRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserService userService;

    public BalanceUsuarioDto getByIdUsuario(){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        BalanceUsuario balanceUsuario = balanceUsuarioRepository.findByUsuarioId(usuario.getId()).orElseThrow();

        //crear o actualizar el historico cada vez que se consulta el balance
        createOrUpdateHistorico(balanceUsuario);
        return new BalanceUsuarioDto(balanceUsuario);
    }

    public Catalogo<BalanceHistoricoDto> findHistoricoIdUsuarioAndFechaAfter(FechaDto fecha){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        List<BalanceHistorico> result = balanceHistoricoRepository
                .findBalanceHistoricosByUsuarioIdAndFechaGreaterThanEqualOrderByFechaAsc(
                        usuario.getId(),
                        fecha.getFecha()
                );

        List<BalanceHistoricoDto> resultList = result.stream()
                .map(BalanceHistoricoDto::new)
                .collect(Collectors.toList());
        return new Catalogo<>(resultList);
    }

    public BalanceHistorico createOrUpdateHistorico(BalanceUsuario balanceUsuario){

        Optional<BalanceHistorico> balanceHistorico = balanceHistoricoRepository.findByUsuarioIdAndFecha(
                balanceUsuario.getUsuario().getId(),
                balanceUsuario.getUltimaActualizacion().toLocalDate()
        );

        if(balanceHistorico.isPresent()){
            BalanceHistorico balanceHistoricoEntity = balanceHistorico.get();
            balanceHistoricoEntity.setUsuario(balanceUsuario.getUsuario());
            balanceHistoricoEntity.setMontoAhorrado(balanceUsuario.getMontoAhorrado());
            balanceHistoricoEntity.setMontoDisponible(balanceUsuario.getMontoDisponible());
            balanceHistoricoEntity.setMontoTotal(balanceUsuario.getBalanceTotal());
            balanceHistoricoEntity.setFecha(balanceUsuario.getFecha());

            return balanceHistoricoRepository.save(balanceHistoricoEntity);
        }else{
            BalanceHistorico balanceHistoricoEntity = new BalanceHistorico();
            balanceHistoricoEntity.setUsuario(balanceUsuario.getUsuario());
            balanceHistoricoEntity.setMontoAhorrado(balanceUsuario.getMontoAhorrado());
            balanceHistoricoEntity.setMontoDisponible(balanceUsuario.getMontoDisponible());
            balanceHistoricoEntity.setMontoTotal(balanceUsuario.getBalanceTotal());
            balanceHistoricoEntity.setFecha(balanceUsuario.getFecha());

            return balanceHistoricoRepository.save(balanceHistoricoEntity);
        }


    }
}
