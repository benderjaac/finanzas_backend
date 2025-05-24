package com.primeng.primeng.controllers;

import com.primeng.primeng.models.Gasto;
import com.primeng.primeng.models.ResponseApi;
import com.primeng.primeng.services.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<ResponseApi<List<Gasto>>> getAllGastos(){
        List<Gasto> gastos = gastoService.getAllGastos();
        ResponseApi<List<Gasto>> response = new ResponseApi<>(this.title, "OK", "Información encontrada", gastos, this.date);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<Gasto>> getGastoById(@PathVariable Long id) {
        Optional<Gasto> gasto = gastoService.getGastoById(id);
        if(gasto.isEmpty()){
            ResponseApi<Gasto> response = new ResponseApi<>(
                    this.title,
                    "ERROR",
                    "Información no encontrada",
                    null,
                    this.date
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseApi<Gasto> response = new ResponseApi<>(
                this.title,
                "OK",
                "Información encontrada",
                gasto.get(),
                this.date
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseApi<Gasto>> createGasto(@RequestBody Gasto gasto) {
        Gasto gastoNew = gastoService.createGasto(gasto);
        ResponseApi<Gasto> response = new ResponseApi<>(
                this.title,
                "OK",
                "Creado exitosamente",
                gastoNew,
                this.date
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi<Gasto>> actualizarGasto(@PathVariable Long id, @RequestBody Gasto gasto) {
        Optional<Gasto> gastoActualizar = gastoService.getGastoById(id);
        if(gastoActualizar.isEmpty()){
            ResponseApi<Gasto> response = new ResponseApi<>(
                    this.title,
                    "ERROR",
                    "Información no encontrada",
                    null,
                    this.date
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Gasto actualizado = gastoService.updateGasto(id, gasto);
        return ResponseEntity.ok(new ResponseApi<>(this.title, "OK", "Actualizado correctamente", actualizado, this.date));
    }

}
