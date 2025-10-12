package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.*;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.BalanceUsuarioService;
import com.primeng.primeng.services.GastoService;
import com.primeng.primeng.services.MovimientoService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private BalanceUsuarioService balanceUsuarioService;

    private Response response = new Response(Type.GASTO);

    @PreAuthorize("hasAuthority('gasto_select')")
    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
        HttpServletRequest request,
        @RequestBody Query query
    ){
        return response.find(movimientoService.findAll(query));
    }

    @PreAuthorize("hasAuthority('gasto_select')")
    @GetMapping("/{id}")
    public ResponseEntity<HttpOk> getById(
            HttpServletRequest request,
            @PathVariable Long id)
    {
        return response.find(movimientoService.getById(id));
    }

    @PreAuthorize("hasAuthority('gasto_insert')")
    @PostMapping
    public ResponseEntity<HttpOk> create(@RequestBody MovimientoCreateDto movimiento) {
        MovimientoDto newMovimiento =  movimientoService.create(movimiento);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();

        return response.create(newMovimiento.getId().toString(), balance);
    }

    @PreAuthorize("hasAuthority('gasto_select')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> update(@PathVariable Long id, @RequestBody MovimientoCreateDto movimiento) {
        MovimientoDto updateMovimiento =  movimientoService.update(id, movimiento);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.update(updateMovimiento.getId().toString(), balance);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> delete(@PathVariable Long id) {
        movimientoService.delete(id);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.delete(id.toString(), balance);
    }



}
