package com.primeng.primeng.services;

import com.primeng.primeng.dto.MovimientoCreateDto;
import com.primeng.primeng.dto.MovimientoDto;
import com.primeng.primeng.exceptions.BadRequestException;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.Categoria;
import com.primeng.primeng.models.Movimiento;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.CategoriaRepository;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.repositories.MovimientoRepository;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.util.Type;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CategoriaRepository categoriaMovimientoRepository;

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
        Movimiento movimiento = createSimple(movimientoCreatedto);

        return new MovimientoDto(movimiento);
    }

    public Movimiento createSimple(MovimientoCreateDto movimientoCreatedto) {
        if (movimientoCreatedto.getCategoriaId() == null) {
            throw new BadRequestException("El ID de la categoria es obligatorio");
        }

        if (movimientoCreatedto.getTipo() == null) {
            throw new BadRequestException("El Tipo es obligatorio");
        }

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        Categoria catMovimiento;
        if(movimientoCreatedto.getCategoriaId()!=108){
            catMovimiento = categoriaMovimientoRepository.findByIdAndUsuarioId(movimientoCreatedto.getCategoriaId(), usuario.getId())
                    .orElseThrow(() -> new BadRequestException("Categoria no encontrada"));
            System.out.println(movimientoCreatedto.getTipo()+"!="+catMovimiento.getTipo());
            if(!movimientoCreatedto.getTipo().equals(catMovimiento.getTipo())){
                throw new BadRequestException("La categoria no es del mismo tipo que el movimiento");
            }
        }else{
            catMovimiento = categoriaMovimientoRepository.findById(movimientoCreatedto.getCategoriaId())
                    .orElseThrow(() -> new BadRequestException("Categoria no encontrada"));
        }

        User userEntity = userService.getUserById(usuario.getId());

        Movimiento movimiento = new Movimiento();
        movimiento.setDescri(movimientoCreatedto.getDescri());
        movimiento.setFecha(movimientoCreatedto.getFecha());
        movimiento.setMonto(movimientoCreatedto.getMonto());
        movimiento.setTipo(movimientoCreatedto.getTipo());
        movimiento.setUsuario(userEntity);
        movimiento.setCategoria(catMovimiento);

        return movimientoRepository.save(movimiento);
    }

    public MovimientoDto update(Long id, MovimientoCreateDto nuevoMovimiento){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Movimiento movimiento = movimientoRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.MOVIMIENTO, id));

        if (nuevoMovimiento.getCategoriaId() == null) {
            throw new BadRequestException("El ID de la categoria es obligatorio");
        }

        Categoria catMovimiento = categoriaMovimientoRepository.findByIdAndUsuarioId(nuevoMovimiento.getCategoriaId(), usuario.getId())
                .orElseThrow(() -> new BadRequestException("Categoria no encontrada"));

        if(nuevoMovimiento.getTipo()!=catMovimiento.getTipo()){
            throw new BadRequestException("La categoria no es del mismo tipo que el movimiento");
        }

        movimiento.setDescri(nuevoMovimiento.getDescri());
        movimiento.setMonto(nuevoMovimiento.getMonto());
        movimiento.setFecha(nuevoMovimiento.getFecha());
        movimiento.setCategoria(catMovimiento);

        return new MovimientoDto(movimientoRepository.save(movimiento));
    }

    public MovimientoDto updateMontoFecha(Long id, Float monto, LocalDate fecha){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Movimiento movimiento = movimientoRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.MOVIMIENTO, id));

        movimiento.setMonto(monto);
        movimiento.setFecha(fecha);

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
