package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.AhorroCreateDto;
import com.primeng.primeng.dto.AhorroDepositoCreateDto;
import com.primeng.primeng.dto.AhorroDepositoDto;
import com.primeng.primeng.dto.AhorroDto;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.AhorroDepositoService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ahorros/depositos")
public class AhorroDepositoController {
    @Autowired
    private AhorroDepositoService ahorroDepositoService;

    private Response response = new Response(Type.AHORRODEPOSITO);

    @PreAuthorize("hasAuthority('ahorro_select')")
    @PostMapping("/data/{id:\\d+}")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request,
            @RequestBody Query query,
            @PathVariable Long id
    ){
        return response.find(ahorroDepositoService.findAll(query, id));
    }

    @PreAuthorize("hasAuthority('ahorro_select')")
    @PostMapping("/{id:\\d+}")
    public ResponseEntity<HttpOk> createAhorroDeposito(
            @RequestBody AhorroDepositoCreateDto ahorroDeposito,
            @PathVariable Long id
    ) {
        AhorroDepositoDto newAhorroDeposito =  ahorroDepositoService.createAhorroDeposito(ahorroDeposito, id);
        return response.create(newAhorroDeposito.getId().toString(), newAhorroDeposito);
    }

    @PreAuthorize("hasAuthority('ahorro_select')")
    @PutMapping("/{idAhorro:\\d+}/{idDeposito:\\d+}")
    public ResponseEntity<HttpOk> updateAhorroDeposito(
            @RequestBody AhorroDepositoCreateDto ahorroDeposito,
            @PathVariable Long idAhorro,
            @PathVariable Long idDeposito
    ) {
        AhorroDepositoDto newAhorroDeposito =  ahorroDepositoService.updateAhorroDeposito(idDeposito,ahorroDeposito, idAhorro);
        return response.update(newAhorroDeposito.getId().toString());
    }
}
