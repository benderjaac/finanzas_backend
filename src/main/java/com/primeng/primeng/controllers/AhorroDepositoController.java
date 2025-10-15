package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.AhorroDepositoCreateDto;
import com.primeng.primeng.dto.AhorroDepositoDto;
import com.primeng.primeng.dto.BalanceUsuarioDto;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.AhorroDepositoService;
import com.primeng.primeng.services.BalanceUsuarioService;
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

    @Autowired
    private BalanceUsuarioService balanceUsuarioService;

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
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.create(newAhorroDeposito.getId().toString(), balance);
    }

    @PreAuthorize("hasAuthority('ahorro_select')")
    @PutMapping("/{idAhorro:\\d+}/{idDeposito:\\d+}")
    public ResponseEntity<HttpOk> updateAhorroDeposito(
            @RequestBody AhorroDepositoCreateDto ahorroDeposito,
            @PathVariable Long idAhorro,
            @PathVariable Long idDeposito
    ) {
        AhorroDepositoDto newAhorroDeposito =  ahorroDepositoService.updateAhorroDeposito(idDeposito,ahorroDeposito, idAhorro);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.update(newAhorroDeposito.getId().toString(), balance);
    }

    @PreAuthorize("hasAuthority('ahorro_select')")
    @DeleteMapping("/{idAhorro:\\d+}/{idDeposito:\\d+}")
    public ResponseEntity<HttpOk> deleteAhorroDeposito(
            @PathVariable Long idAhorro,
            @PathVariable Long idDeposito
    ) {
        ahorroDepositoService.deleteAhorroDeposito(idAhorro, idDeposito);
        BalanceUsuarioDto balance = balanceUsuarioService.getByIdUsuario();
        return response.delete(idDeposito.toString(), balance);
    }
}
