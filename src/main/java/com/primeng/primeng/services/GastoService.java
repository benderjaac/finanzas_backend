package com.primeng.primeng.services;

import com.primeng.primeng.dto.GastoCreateDto;
import com.primeng.primeng.dto.GastoDto;
import com.primeng.primeng.exceptions.BadRequestException;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.Gasto;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.CategoriaGastoRepository;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.repositories.GastoRepository;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private CategoriaGastoRepository categoriaGastoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DBRepository db;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public Result<GastoDto> findAll(Query query){
        query.addFetch("categoria");
        Result<Gasto> result =  db.findAll(Gasto.class, query, true);
        List<GastoDto> resultList =  result.getData().stream()
                .map(GastoDto::new)
                .collect(Collectors.toList());
        return new Result<>(resultList, result.getPagination());
    }

    public GastoDto getGastoById(Long id) {
        Gasto gasto = gastoRepository.findById(id).orElseThrow(() -> new NotFoundException(Type.USUARIO, id));
        return new GastoDto(gasto);
    }

    public GastoDto createGasto(GastoCreateDto gastodto) {
        // Validación de datos mínimos
        if (gastodto.getCategoriaId() == null) {
            throw new BadRequestException("El ID de la categoria es obligatorio");
        }

        // Buscar la categoria
        CategoriaGasto catGasto = categoriaGastoRepository.findById(gastodto.getCategoriaId())
                .orElseThrow(() -> new BadRequestException("Categoria no encontrada"));

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        User userEntity = userService.getUserById(usuario.getId());

        // Crear entidad Gasto
        Gasto gasto = new Gasto();
        gasto.setDescri(gastodto.getDescri());
        gasto.setFecha(gastodto.getFecha());
        gasto.setMonto(gastodto.getMonto());
        gasto.setContado(false);
        gasto.setUsuario(userEntity);
        gasto.setCategoria(catGasto);

        return new GastoDto(gastoRepository.save(gasto));
    }

    public Gasto updateGasto(Long id, Gasto nuevoGasto){
        return gastoRepository.findById(id).map(gastoExistente -> {
            gastoExistente.setDescri(nuevoGasto.getDescri());
            gastoExistente.setMonto(nuevoGasto.getMonto());
            gastoExistente.setFecha(nuevoGasto.getFecha());
            // Actualiza otros campos según tu modelo

            return gastoRepository.save(gastoExistente);
        }).orElseThrow(() -> new RuntimeException("Gasto no encontrado con id " + id));
    }

    public void deleteGasto(Long id) {
        gastoRepository.deleteById(id);
    }
}
