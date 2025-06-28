package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.CategoriaIngresoCreateDto;
import com.primeng.primeng.dto.CategoriaIngresoDto;
import com.primeng.primeng.dto.CategoriaIngresoCreateDto;
import com.primeng.primeng.dto.CategoriaIngresoDto;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.CategoriaIngresoService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categoria/ingreso")
public class CategoriaIngresoController {
    private Response response = new Response(Type.CATEGORIAINGRESO);

    @Autowired
    private CategoriaIngresoService categoriaIngresoService;

    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request,
            @RequestBody Query query
    ){
        return response.find(categoriaIngresoService.findAll(query));
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<HttpOk> findById(
            HttpServletRequest request,
            @PathVariable Long id
    ){
        System.out.println("Entrando al metodo");
        return response.find(categoriaIngresoService.getByID(id));
    }

    @GetMapping("/catalogo")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request
    ){
        return response.find(categoriaIngresoService.findAll());
    }

    @PreAuthorize("hasAuthority('categorias_ingreso_insert')")
    @PostMapping
    public ResponseEntity<HttpOk> createCategoriaIngreso(@RequestBody CategoriaIngresoCreateDto catIngreso) {
        CategoriaIngresoDto newCatIngreso =  categoriaIngresoService.createCategoria(catIngreso);
        return response.create(newCatIngreso.getId().toString(), newCatIngreso);
    }

    @PreAuthorize("hasAuthority('categorias_ingreso_select')")
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<HttpOk> updateCategoriaIngreso(@PathVariable Long id, @RequestBody CategoriaIngresoCreateDto categoriaIngreso) {
        CategoriaIngresoDto newCategoriaIngreso =  categoriaIngresoService.updateCategoriaIngreso(id, categoriaIngreso);
        return response.update(newCategoriaIngreso.getId().toString());
    }

    @PreAuthorize("hasAuthority('categorias_ingreso_select')")
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<HttpOk> deleteCategoriaIngreso(@PathVariable Long id) {
        categoriaIngresoService.deleteCategoriaIngreso(id);
        return response.delete(id.toString());
    }
}
