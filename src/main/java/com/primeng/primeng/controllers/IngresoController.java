package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.IngresoCreateDto;
import com.primeng.primeng.dto.IngresoDto;
import com.primeng.primeng.models.ResponseApi;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.IngresoService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {
    @Autowired
    private IngresoService ingresoService;

    private Response response = new Response(Type.GASTO);

    @PreAuthorize("hasAuthority('ingreso_select')")
    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request,
            @RequestBody Query query
    ){
        return response.find(ingresoService.findAll(query));
    }

    @PreAuthorize("hasAuthority('ingreso_select')")
    @GetMapping("/{id}")
    public ResponseEntity<HttpOk> getIngresoById(
            HttpServletRequest request,
            @PathVariable Long id)
    {
        return response.find(ingresoService.getIngresoById(id));
    }

    @PreAuthorize("hasAuthority('ingreso_insert')")
    @PostMapping
    public ResponseEntity<HttpOk> createIngreso(@RequestBody IngresoCreateDto ingreso) {
        IngresoDto newIngreso =  ingresoService.createIngreso(ingreso);
        return response.create(newIngreso.getId().toString(), newIngreso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deleteIngreso(@PathVariable Long id) {
        ingresoService.deleteIngreso(id);
        return response.delete(id.toString());
    }
}
