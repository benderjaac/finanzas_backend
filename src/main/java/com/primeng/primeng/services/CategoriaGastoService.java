package com.primeng.primeng.services;

import com.primeng.primeng.dto.UserSimpleDto;
import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.CategoriaGastoRepository;
import com.primeng.primeng.repositories.DBRepository;
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

    public Result<CategoriaGasto> findAll(Query query){
        return db.findAll(CategoriaGasto.class, query);
    }
}
