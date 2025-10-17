package com.primeng.primeng.services;

import com.primeng.primeng.dto.MovimientoDto;
import com.primeng.primeng.dto.MovimientoResumenMensualDto;
import com.primeng.primeng.models.Movimiento;
import com.primeng.primeng.models.db.Catalogo;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.repositories.MovimientoRepository;
import com.primeng.primeng.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadisticasService {
    @Autowired
    private DBRepository db;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    MovimientoRepository movimientoRepository;

    public Catalogo<MovimientoResumenMensualDto> getMovimientosTotalesMes(){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Long usuarioId = usuario.getId();
        List<Object[]> result = movimientoRepository.obtenerTotalesPorMesYUsuario(usuarioId);

        List<MovimientoResumenMensualDto> resultList = result.stream()
                .map(MovimientoResumenMensualDto::new)
                .collect(Collectors.toList());
        return new Catalogo<>(resultList);
    }

    public Catalogo<MovimientoDto> getGastos3Mes(){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Long usuarioId = usuario.getId();

        List<Movimiento> result = movimientoRepository.getGastos3Mes(usuarioId);

        List<MovimientoDto> resultList = result.stream()
                .map(MovimientoDto::new)
                .collect(Collectors.toList());
        return new Catalogo<>(resultList);
    }
}
