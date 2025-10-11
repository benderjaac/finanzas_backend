package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.*;
import com.primeng.primeng.models.Gasto;
import com.primeng.primeng.models.ResponseApi;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.BalanceUsuarioService;
import com.primeng.primeng.services.GastoService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private BalanceUsuarioService balanceUsuarioService;

    private Response response = new Response(Type.GASTO);

    @PreAuthorize("hasAuthority('gasto_select')")
    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
        HttpServletRequest request,
        @RequestBody Query query
    ){
        return response.find(gastoService.findAll(query));
    }

    @PreAuthorize("hasAuthority('gasto_select')")
    @GetMapping("/{id}")
    public ResponseEntity<HttpOk> getGastoById(
            HttpServletRequest request,
            @PathVariable Long id)
    {
        return response.find(gastoService.getGastoById(id));
    }

    @PreAuthorize("hasAuthority('gasto_insert')")
    @PostMapping
    public ResponseEntity<HttpOk> createGasto(@RequestBody GastoCreateDto gasto) {
        GastoDto newGasto =  gastoService.createGasto(gasto);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();

        return response.create(newGasto.getId().toString(), balance);
    }

    @PreAuthorize("hasAuthority('gasto_select')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> updateGasto(@PathVariable Long id, @RequestBody GastoCreateDto gasto) {
        GastoDto updateGasto =  gastoService.updateGasto(id, gasto);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.update(updateGasto.getId().toString(), balance);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deleteGasto(@PathVariable Long id) {
        gastoService.deleteGasto(id);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.delete(id.toString(), balance);
    }



}
