package com.primeng.primeng.services;

import com.primeng.primeng.dto.CategoriaGastoCreateDto;
import com.primeng.primeng.dto.CategoriaGastoDto;
import com.primeng.primeng.dto.GastoCreateDto;
import com.primeng.primeng.dto.GastoDto;
import com.primeng.primeng.exceptions.BadRequestException;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.Gasto;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Catalogo;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.CategoriaGastoRepository;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoriaGastoService {
    @Autowired
    private CategoriaGastoRepository categoriaGastoRepository;

    @Autowired
    private DBRepository db;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserService userService;

    //categorias por query
    public Result<CategoriaGastoDto> findAll(Query query){
        Result<CategoriaGasto> result = db.findAll(CategoriaGasto.class, query, true);
        List<CategoriaGastoDto> resultList = result.getData().stream()
                .map(CategoriaGastoDto::new)
                .collect(Collectors.toList());
        return new Result<>(resultList, result.getPagination());
    }

    //categorias como catalogo (todas)
    public Catalogo<CategoriaGastoDto> findAll(){

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        List<CategoriaGasto> result = categoriaGastoRepository.findByUsuarioIdAndVisibleTrue(usuario.getId());

        List<CategoriaGastoDto> resultList = result.stream()
                .map(CategoriaGastoDto::new)
                .collect(Collectors.toList());
        return new Catalogo<>(resultList);
    }

    //categoria por id
    public CategoriaGastoDto getByID(Long id){
        CategoriaGasto result = categoriaGastoRepository.findById(id).orElseThrow(() -> new NotFoundException(Type.CATEGORIAGASTO, id));
        return new CategoriaGastoDto(result);
    }

    public CategoriaGastoDto createCategoria(CategoriaGastoCreateDto catGastodto) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        User userEntity = userService.getUserById(usuario.getId());

        // Crear entidad CategoriaGasto
        CategoriaGasto categoriaGasto = new CategoriaGasto();

        categoriaGasto.setNombre(catGastodto.getNombre());
        categoriaGasto.setDescri(catGastodto.getDescri());
        categoriaGasto.setColor(catGastodto.getColor());
        categoriaGasto.setIcon(catGastodto.getIcon());
        categoriaGasto.setUsuario(userEntity);



        return new CategoriaGastoDto(categoriaGastoRepository.save(categoriaGasto));
    }
}
