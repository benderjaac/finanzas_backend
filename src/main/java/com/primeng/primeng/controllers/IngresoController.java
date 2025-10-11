package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.BalanceUsuarioDto;
import com.primeng.primeng.dto.IngresoCreateDto;
import com.primeng.primeng.dto.IngresoDto;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.BalanceUsuarioService;
import com.primeng.primeng.services.IngresoService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {
    @Autowired
    private IngresoService ingresoService;

    @Autowired
    private BalanceUsuarioService balanceUsuarioService;

    private Response response = new Response(Type.INGRESO);

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
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.create(newIngreso.getId().toString(), balance);
    }

    @PreAuthorize("hasAuthority('ingreso_select')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> updateIngreso(@PathVariable Long id, @RequestBody IngresoCreateDto ingreso) {
        IngresoDto newIngreso =  ingresoService.updateIngreso(id, ingreso);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.update(newIngreso.getId().toString(), balance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deleteIngreso(@PathVariable Long id) {
        ingresoService.deleteIngreso(id);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.delete(id.toString(), balance);
    }
}
