package com.primeng.primeng.services;

import com.primeng.primeng.dto.CategoriaGastoDto;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.CategoriaGastoRepository;
import com.primeng.primeng.repositories.DBRepository;
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

    public Result<CategoriaGastoDto> findAll(Query query){
        Result<CategoriaGasto> result = db.findAll(CategoriaGasto.class, query, true);
        List<CategoriaGastoDto> resultList = result.getData().stream()
                .map(CategoriaGastoDto::new)
                .collect(Collectors.toList());
        return new Result<>(resultList, result.getPagination());
    }

    public CategoriaGastoDto getByID(Long id){
        CategoriaGasto result = categoriaGastoRepository.findById(id).orElseThrow(() -> new NotFoundException(Type.CATEGORIAGASTO, id));
        return new CategoriaGastoDto(result);
    }
}
