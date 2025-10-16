package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.CategoriaCreateDto;
import com.primeng.primeng.dto.CategoriaDto;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.CategoriaService;
import com.primeng.primeng.services.EstadisticasService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/estadisticas")
public class EstadisticasController {
    private Response response = new Response(Type.ESTADISTICA);

    @Autowired
    private EstadisticasService estadisticasService;

    //@PreAuthorize("hasAuthority('categorias_select')")
    @GetMapping("/totalesmes")
    public ResponseEntity<HttpOk> getTotalesMes(
            HttpServletRequest request
    ){
        return response.find(estadisticasService.getMovimientosTotalesMes());
    }


}
