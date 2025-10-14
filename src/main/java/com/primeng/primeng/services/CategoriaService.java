package com.primeng.primeng.services;

import com.primeng.primeng.dto.*;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.Categoria;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Catalogo;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.CategoriaRepository;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private DBRepository db;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserService userService;

    //categorias por query
    public Result<CategoriaDto> findAll(Query query){
        Result<Categoria> result = db.findAll(Categoria.class, query, true);
        List<CategoriaDto> resultList = result.getData().stream()
                .map(CategoriaDto::new)
                .collect(Collectors.toList());
        return new Result<>(resultList, result.getPagination());
    }

    //categorias como catalogo (todas)
    public Catalogo<CategoriaDto> findAll(){

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        List<Categoria> result = categoriaRepository.findByUsuarioIdAndVisibleTrue(usuario.getId());

        List<CategoriaDto> resultList = result.stream()
                .map(CategoriaDto::new)
                .collect(Collectors.toList());
        return new Catalogo<>(resultList);
    }

    //categoria por id
    public CategoriaDto getByID(Long id){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        Categoria result = categoriaRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.CATEGORIAGASTO, id));
        return new CategoriaDto(result);
    }

    public CategoriaDto createCategoria(CategoriaCreateDto catGastodto) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        User userEntity = userService.getUserById(usuario.getId());

        // Crear entidad CategoriaGasto
        Categoria categoria = new Categoria();

        categoria.setNombre(catGastodto.getNombre());
        categoria.setDescri(catGastodto.getDescri());
        categoria.setColor(catGastodto.getColor());
        categoria.setIcon(catGastodto.getIcon());
        categoria.setUsuario(userEntity);
        categoria.setVisible(true);

        return new CategoriaDto(categoriaRepository.save(categoria));
    }

    public CategoriaDto updateCategoriaGasto(Long id, CategoriaCreateDto nuevaCat){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.CATEGORIAGASTO, id));

        categoria.setDescri(nuevaCat.getDescri());
        categoria.setNombre(nuevaCat.getNombre());
        categoria.setColor(nuevaCat.getColor());
        categoria.setIcon(nuevaCat.getIcon());

        return new CategoriaDto(categoriaRepository.save(categoria));
    }

    public void deleteCategoriaGasto(Long id) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        int deleted = categoriaRepository.deleteByIdAndUsuarioId(id, usuario.getId());
        if (deleted == 0) {
            throw new NotFoundException(Type.CATEGORIAGASTO, id);
        }
    }
}
