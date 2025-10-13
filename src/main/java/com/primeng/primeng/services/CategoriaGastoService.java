package com.primeng.primeng.services;

import com.primeng.primeng.dto.*;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.CategoriaGasto;
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
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        CategoriaGasto result = categoriaGastoRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.CATEGORIAGASTO, id));
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
        categoriaGasto.setVisible(true);

        return new CategoriaGastoDto(categoriaGastoRepository.save(categoriaGasto));
    }

    public CategoriaGastoDto updateCategoriaGasto(Long id, CategoriaGastoCreateDto nuevaCat){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        CategoriaGasto categoria = categoriaGastoRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.CATEGORIAGASTO, id));

        categoria.setDescri(nuevaCat.getDescri());
        categoria.setNombre(nuevaCat.getNombre());
        categoria.setColor(nuevaCat.getColor());
        categoria.setIcon(nuevaCat.getIcon());

        return new CategoriaGastoDto(categoriaGastoRepository.save(categoria));
    }

    public void deleteCategoriaGasto(Long id) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        int deleted = categoriaGastoRepository.deleteByIdAndUsuarioId(id, usuario.getId());
        if (deleted == 0) {
            throw new NotFoundException(Type.CATEGORIAGASTO, id);
        }
    }
}
