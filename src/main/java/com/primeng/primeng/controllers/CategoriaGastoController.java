package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.CategoriaGastoCreateDto;
import com.primeng.primeng.dto.CategoriaGastoDto;
import com.primeng.primeng.dto.GastoCreateDto;
import com.primeng.primeng.dto.GastoDto;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.CategoriaGastoService;
import com.primeng.primeng.services.UserService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/categoria/gasto")
public class CategoriaGastoController {
    private Response response = new Response(Type.CATEGORIAGASTO);

    @Autowired
    private CategoriaGastoService categoriaGastoService;

    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
        HttpServletRequest request,
        @RequestBody Query query
    ){
        return response.find(categoriaGastoService.findAll(query));
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<HttpOk> findById(
            HttpServletRequest request,
            @PathVariable Long id
    ){
        return response.find(categoriaGastoService.getByID(id));
    }

    @GetMapping("/catalogo")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request
    ){
        return response.find(categoriaGastoService.findAll());
    }

    @PreAuthorize("hasAuthority('categorias_gasto_insert')")
    @PostMapping
    public ResponseEntity<HttpOk> createCategoriaGasto(@RequestBody CategoriaGastoCreateDto catGasto) {
        CategoriaGastoDto newCatGasto =  categoriaGastoService.createCategoria(catGasto);
        return response.create(newCatGasto.getId().toString(), newCatGasto);
    }
}
