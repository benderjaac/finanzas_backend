package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.AhorroCreateDto;
import com.primeng.primeng.dto.AhorroDto;
import com.primeng.primeng.dto.GastoCreateDto;
import com.primeng.primeng.dto.GastoDto;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.AhorroService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ahorros")
public class AhorroController {

    @Autowired
    private AhorroService ahorroService;

    private Response response = new Response(Type.AHORRO);

    @PreAuthorize("hasAuthority('ahorro_select')")
    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request
    ){
        return response.find(ahorroService.findAll());
    }

    @PreAuthorize("hasAuthority('ahorro_select')")
    @GetMapping("/{id}")
    public ResponseEntity<HttpOk> getAhorroById(
            HttpServletRequest request,
            @PathVariable Long id)
    {
        return response.find(ahorroService.getByID(id));
    }

    @PreAuthorize("hasAuthority('ahorro_select')")
    @PostMapping
    public ResponseEntity<HttpOk> createAhorro(@RequestBody AhorroCreateDto ahorro) {
        AhorroDto newAhorro =  ahorroService.createAhorro(ahorro);
        return response.create(newAhorro.getId().toString(), newAhorro);
    }

    @PreAuthorize("hasAuthority('ahorro_select')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> updateAhorro(@PathVariable Long id, @RequestBody AhorroCreateDto ahorro) {
        AhorroDto newAhorro =  ahorroService.updateAhorro(id, ahorro);
        return response.update(newAhorro.getId().toString());
    }

    @PreAuthorize("hasAuthority('ahorro_select')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deleteAhorro(@PathVariable Long id) {
        ahorroService.deleteAhorro(id);
        return response.delete(id.toString());
    }

}
