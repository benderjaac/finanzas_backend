package com.primeng.primeng.services;

import com.primeng.primeng.dto.MovimientoCreateDto;
import com.primeng.primeng.dto.MovimientoDto;
import com.primeng.primeng.exceptions.BadRequestException;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.Movimiento;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.CategoriaGastoRepository;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.repositories.MovimientoRepository;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.util.Type;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CategoriaGastoRepository categoriaMovimientoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DBRepository db;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public Result<MovimientoDto> findAll(Query query){
        query.addFetch("categoria");
        Result<Movimiento> result =  db.findAll(Movimiento.class, query, true);
        List<MovimientoDto> resultList =  result.getData().stream()
                .map(MovimientoDto::new)
                .collect(Collectors.toList());
        return new Result<>(resultList, result.getPagination());
    }

    public MovimientoDto getById(Long id) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        Movimiento movimiento = movimientoRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.MOVIMIENTO, id));
        return new MovimientoDto(movimiento);
    }

    public MovimientoDto create(MovimientoCreateDto movimientoCreatedto) {
        // Validación de datos mínimos
        if (movimientoCreatedto.getCategoriaId() == null) {
            throw new BadRequestException("El ID de la categoria es obligatorio");
        }

        if (movimientoCreatedto.getTipo() == null) {
            throw new BadRequestException("El Tipo es obligatorio");
        }

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        // Buscar la categoria
        CategoriaGasto catMovimiento = categoriaMovimientoRepository.findByIdAndUsuarioId(movimientoCreatedto.getCategoriaId(), usuario.getId())
                .orElseThrow(() -> new BadRequestException("Categoria no encontrada"));


        User userEntity = userService.getUserById(usuario.getId());

        // Crear entidad Movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setDescri(movimientoCreatedto.getDescri());
        movimiento.setFecha(movimientoCreatedto.getFecha());
        movimiento.setMonto(movimientoCreatedto.getMonto());
        movimiento.setUsuario(userEntity);
        movimiento.setCategoria(catMovimiento);

        return new MovimientoDto(movimientoRepository.save(movimiento));
    }

    public MovimientoDto update(Long id, MovimientoCreateDto nuevoMovimiento){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Movimiento movimiento = movimientoRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.MOVIMIENTO, id));
        // Validación de datos mínimos
        if (nuevoMovimiento.getCategoriaId() == null) {
            throw new BadRequestException("El ID de la categoria es obligatorio");
        }

        // Buscar la categoria
        CategoriaGasto catMovimiento = categoriaMovimientoRepository.findByIdAndUsuarioId(nuevoMovimiento.getCategoriaId(), usuario.getId())
                .orElseThrow(() -> new BadRequestException("Categoria no encontrada"));

        movimiento.setDescri(nuevoMovimiento.getDescri());
        movimiento.setMonto(nuevoMovimiento.getMonto());
        movimiento.setFecha(nuevoMovimiento.getFecha());
        movimiento.setTipo(nuevoMovimiento.getTipo());
        movimiento.setCategoria(catMovimiento);

        return new MovimientoDto(movimientoRepository.save(movimiento));
    }
    @Transactional
    public void delete(Long id) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        int deleted = movimientoRepository.deleteByIdAndUsuarioId(id, usuario.getId());
        if (deleted == 0) {
            throw new NotFoundException(Type.MOVIMIENTO, id);
        }
    }
}
