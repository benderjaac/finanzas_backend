package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.CategoriaCreateDto;
import com.primeng.primeng.dto.CategoriaDto;
import com.primeng.primeng.dto.FechaDto;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.BalanceUsuarioService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/balance")
public class BalanceUsuarioController {

    @Autowired
    private BalanceUsuarioService balanceUsuarioService;

    private Response response = new Response(Type.BALANCE_USUARIO);

    @GetMapping("/usuario")
    public ResponseEntity<HttpOk> getBalanceByIdUsuario(HttpServletRequest request) {
        return response.find(balanceUsuarioService.getByIdUsuario());
    }

    @PostMapping("/historico")
    public ResponseEntity<HttpOk> findHistoricoByidUsuarioAndFechaAfter(
            @RequestBody FechaDto fecha) {
        return response.find(balanceUsuarioService.findHistoricoIdUsuarioAndFechaAfter(fecha));
    }

}
