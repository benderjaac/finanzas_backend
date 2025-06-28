package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.AhorroCreateDto;
import com.primeng.primeng.dto.AhorroDto;
import com.primeng.primeng.dto.GastoCreateDto;
import com.primeng.primeng.dto.GastoDto;
import com.primeng.primeng.models.Gasto;
import com.primeng.primeng.models.ResponseApi;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
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
        return response.create(newGasto.getId().toString(), newGasto);
    }

    @PreAuthorize("hasAuthority('gasto_select')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> updateGasto(@PathVariable Long id, @RequestBody GastoCreateDto gasto) {
        GastoDto newGasto =  gastoService.updateGasto(id, gasto);
        return response.update(newGasto.getId().toString());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deleteGasto(@PathVariable Long id) {
        gastoService.deleteGasto(id);
        return response.delete(id.toString());
    }



}
