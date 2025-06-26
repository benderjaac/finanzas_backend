package com.primeng.primeng.services;

import com.primeng.primeng.dto.CategoriaIngresoCreateDto;
import com.primeng.primeng.dto.CategoriaIngresoDto;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.CategoriaIngreso;
import com.primeng.primeng.models.CategoriaIngreso;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Catalogo;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.CategoriaIngresoRepository;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaIngresoService {
    @Autowired
    private CategoriaIngresoRepository categoriaIngresoRepository;

    @Autowired
    private DBRepository db;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserService userService;

    //categorias de ingreso por query
    public Result<CategoriaIngresoDto> findAll(Query query){
        Result<CategoriaIngreso> result = db.findAll(CategoriaIngreso.class, query, true);
        List<CategoriaIngresoDto> resultList = result.getData().stream()
                .map(CategoriaIngresoDto::new)
                .collect(Collectors.toList());
        return new Result<>(resultList, result.getPagination());
    }

    //categorias como catalogo (todas)
    public Catalogo<CategoriaIngresoDto> findAll(){

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();

        List<CategoriaIngreso> result = categoriaIngresoRepository.findByUsuarioIdAndVisibleTrue(usuario.getId());

        List<CategoriaIngresoDto> resultList = result.stream()
                .map(CategoriaIngresoDto::new)
                .collect(Collectors.toList());
        return new Catalogo<>(resultList);
    }

    //categoria por id
    public CategoriaIngresoDto getByID(Long id){
        CategoriaIngreso result = categoriaIngresoRepository.findById(id).orElseThrow(() -> new NotFoundException(Type.CATEGORIAINGRESO, id));
        return new CategoriaIngresoDto(result);
    }

    public CategoriaIngresoDto createCategoria(CategoriaIngresoCreateDto catIngresodto) {
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        User userEntity = userService.getUserById(usuario.getId());

        // Crear entidad CategoriaIngreso
        CategoriaIngreso categoriaIngreso = new CategoriaIngreso();

        categoriaIngreso.setNombre(catIngresodto.getNombre());
        categoriaIngreso.setDescri(catIngresodto.getDescri());
        categoriaIngreso.setColor(catIngresodto.getColor());
        categoriaIngreso.setIcon(catIngresodto.getIcon());
        categoriaIngreso.setUsuario(userEntity);
        categoriaIngreso.setVisible(true);



        return new CategoriaIngresoDto(categoriaIngresoRepository.save(categoriaIngreso));
    }
}
