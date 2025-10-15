package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.*;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.CategoriaService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/categoria")
public class CategoriaController {
    private Response response = new Response(Type.CATEGORIAGASTO);

    @Autowired
    private CategoriaService categoriaService;

    @PreAuthorize("hasAuthority('categorias_select')")
    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
        HttpServletRequest request,
        @RequestBody Query query
    ){
        return response.find(categoriaService.findAll(query));
    }

    @PreAuthorize("hasAuthority('categorias_select')")
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<HttpOk> findById(
            HttpServletRequest request,
            @PathVariable Long id
    ){
        return response.find(categoriaService.getByID(id));
    }

    @PreAuthorize("hasAuthority('categorias_select')")
    @GetMapping("/catalogo")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request
    ){
        return response.find(categoriaService.findAll());
    }

    @PreAuthorize("hasAuthority('categorias_select')")
    @GetMapping("/catalogo/gastos")
    public ResponseEntity<HttpOk> findAllGastos(
            HttpServletRequest request
    ){
        return response.find(categoriaService.findAllTipo("Gasto"));
    }

    @PreAuthorize("hasAuthority('categorias_select')")
    @GetMapping("/catalogo/ingresos")
    public ResponseEntity<HttpOk> findAllIngresos(
            HttpServletRequest request
    ){
        return response.find(categoriaService.findAllTipo("Ingreso"));
    }

    @PreAuthorize("hasAuthority('categorias_insert')")
    @PostMapping
    public ResponseEntity<HttpOk> createCategoriaGasto(@RequestBody CategoriaCreateDto catGasto) {
        CategoriaDto newCatGasto =  categoriaService.createCategoria(catGasto);
        return response.create(newCatGasto.getId().toString(), newCatGasto);
    }

    @PreAuthorize("hasAuthority('categorias_insert')")
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<HttpOk> updateCategoriaGasto(@PathVariable Long id, @RequestBody CategoriaCreateDto categoriaGasto) {
        CategoriaDto newCategoriaGasto =  categoriaService.updateCategoriaGasto(id, categoriaGasto);
        return response.update(newCategoriaGasto.getId().toString());
    }

    @PreAuthorize("hasAuthority('categorias_insert')")
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<HttpOk> deleteCategoriaGasto(@PathVariable Long id) {
        categoriaService.deleteCategoriaGasto(id);
        return response.delete(id.toString());
    }
}
