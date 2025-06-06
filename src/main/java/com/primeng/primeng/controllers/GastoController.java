package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.GastoCreateDTO;
import com.primeng.primeng.dto.GastoDTO;
import com.primeng.primeng.dto.UserCreateDto;
import com.primeng.primeng.dto.UserSimpleDto;
import com.primeng.primeng.models.Gasto;
import com.primeng.primeng.models.ResponseApi;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.services.GastoService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    private String title="Gasto";
    private Date date = new Date();

    @Autowired
    private GastoService gastoService;

    @Autowired
    private DBRepository db;

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
    public ResponseEntity<HttpOk> createGasto(@RequestBody GastoCreateDTO gasto) {
        GastoDTO newGasto =  gastoService.createGasto(gasto);
        return response.create(newGasto.getId().toString(), newGasto);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteGasto(@PathVariable Long id) {
        gastoService.deleteGasto(id);
        ResponseApi response = new ResponseApi<>(
                this.title,
                "OK",
                "Eliminado exitosamente",
                null,
                this.date
        );

        return ResponseEntity.ok(response);
    }



}
