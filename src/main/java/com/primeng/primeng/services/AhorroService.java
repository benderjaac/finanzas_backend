package com.primeng.primeng.services;

import com.primeng.primeng.dto.*;
import com.primeng.primeng.dto.AhorroDto;
import com.primeng.primeng.dto.AhorroDto;
import com.primeng.primeng.exceptions.BadRequestException;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.*;
import com.primeng.primeng.models.Ahorro;
import com.primeng.primeng.models.db.Catalogo;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.AhorroRepository;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AhorroService {
    @Autowired
    private AhorroRepository ahorroRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DBRepository db;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    //obtener todos los registros
    public Catalogo<AhorroDto> findAll(){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        List<Ahorro> result = ahorroRepository.findByUsuarioId(usuario.getId());
        List<AhorroDto> resultList = result.stream()
                .map(AhorroDto::new)
                .collect(Collectors.toList());
        return new Catalogo<>(resultList);
    }

    //ahorro por id
    public AhorroDto getByID(Long id){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        Ahorro result = ahorroRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.AHORRO, id));
        return new AhorroDto(result);
    }

    public AhorroDto createAhorro(AhorroCreateDto ahorroCdto) {

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        User userEntity = userService.getUserById(usuario.getId());

        // Crear entidad Ahorro
        Ahorro ahorro = new Ahorro();
        ahorro.setDescri(ahorroCdto.getDescri());
        ahorro.setFecha_inicio(ahorroCdto.getFecha_inicio());
        ahorro.setMonto_actual(ahorroCdto.getMonto_actual());
        ahorro.setMonto_meta(ahorroCdto.getMonto_meta());
        ahorro.setUsuario(userEntity);

        return new AhorroDto(ahorroRepository.save(ahorro));
    }

    public AhorroDto updateAhorro(Long id, AhorroCreateDto nuevoAhorro){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Ahorro ahorro = ahorroRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.AHORRO, id));

        ahorro.setFecha_inicio(nuevoAhorro.getFecha_inicio());
        ahorro.setDescri(nuevoAhorro.getDescri());
        ahorro.setMonto_actual(nuevoAhorro.getMonto_actual());
        ahorro.setMonto_meta(nuevoAhorro.getMonto_meta());
        ahorro.setFecha_inicio(nuevoAhorro.getFecha_inicio());

        return new AhorroDto(ahorroRepository.save(ahorro));
    }

    public void deleteAhorro(Long id) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        int deleted = ahorroRepository.deleteByIdAndUsuarioId(id, usuario.getId());
        if (deleted == 0) {
            throw new NotFoundException(Type.AHORRO, id);
        }
    }
}
