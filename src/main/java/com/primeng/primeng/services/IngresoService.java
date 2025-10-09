package com.primeng.primeng.services;

import com.primeng.primeng.dto.IngresoCreateDto;
import com.primeng.primeng.dto.IngresoDto;
import com.primeng.primeng.dto.IngresoCreateDto;
import com.primeng.primeng.dto.IngresoDto;
import com.primeng.primeng.exceptions.BadRequestException;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.*;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.CategoriaIngresoRepository;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.repositories.IngresoRepository;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private CategoriaIngresoRepository categoriaIngresoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DBRepository db;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public Result<IngresoDto> findAll(Query query){
        query.addFetch("categoria");
        Result<Ingreso> result =  db.findAll(Ingreso.class, query, true);
        List<IngresoDto> resultList =  result.getData().stream()
                .map(IngresoDto::new)
                .collect(Collectors.toList());
        return new Result<>(resultList, result.getPagination());
    }

    public IngresoDto getIngresoById(Long id) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        Ingreso ingreso = ingresoRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.INGRESO, id));
        return new IngresoDto(ingreso);
    }

    public IngresoDto createIngreso(IngresoCreateDto ingresodto) {
        // Validación de datos mínimos
        if (ingresodto.getCategoriaId() == null) {
            throw new BadRequestException("El ID de la categoria es obligatorio");
        }

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        // Buscar la categoria
        CategoriaIngreso catIngreso = categoriaIngresoRepository.findByIdAndUsuarioId(ingresodto.getCategoriaId(), usuario.getId())
                .orElseThrow(() -> new BadRequestException("Categoria no encontrada"));


        User userEntity = userService.getUserById(usuario.getId());

        // Crear entidad Ingreso
        Ingreso ingreso = new Ingreso();
        ingreso.setDescri(ingresodto.getDescri());
        ingreso.setFecha(ingresodto.getFecha());
        ingreso.setMonto(ingresodto.getMonto());
        ingreso.setUsuario(userEntity);
        ingreso.setCategoria(catIngreso);

        return new IngresoDto(ingresoRepository.save(ingreso));
    }

    public IngresoDto updateIngreso(Long id, IngresoCreateDto nuevoIngreso){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Ingreso ingreso = ingresoRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.GASTO, id));
        // Validación de datos mínimos
        if (nuevoIngreso.getCategoriaId() == null) {
            throw new BadRequestException("El ID de la categoria es obligatorio");
        }

        // Buscar la categoria
        CategoriaIngreso catIngreso = categoriaIngresoRepository.findByIdAndUsuarioId(nuevoIngreso.getCategoriaId(), usuario.getId())
                .orElseThrow(() -> new BadRequestException("Categoria no encontrada"));

        ingreso.setDescri(nuevoIngreso.getDescri());
        ingreso.setMonto(nuevoIngreso.getMonto());
        ingreso.setFecha(nuevoIngreso.getFecha());
        ingreso.setCategoria(catIngreso);

        return new IngresoDto(ingresoRepository.save(ingreso));
    }

    public void deleteIngreso(Long id) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        int deleted = ingresoRepository.deleteByIdAndUsuarioId(id, usuario.getId());
        if (deleted == 0) {
            throw new NotFoundException(Type.INGRESO, id);
        }

    }
}
