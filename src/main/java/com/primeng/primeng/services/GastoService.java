package com.primeng.primeng.services;

import com.primeng.primeng.dto.CategoriaGastoDTO;
import com.primeng.primeng.dto.GastoDTO;
import com.primeng.primeng.models.CategoriaGasto;
import com.primeng.primeng.models.Gasto;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.repositories.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private DBRepository db;

    public Result<GastoDTO> findAll(Query query){
        query.addFetch("categoria");
        Result<Gasto> result =  db.findAll(Gasto.class, query, true);
        List<GastoDTO> resultList =  result.getData().stream()
                .map(GastoDTO::new)
                .collect(Collectors.toList());
        return new Result<>(resultList, result.getPagination());
    }

    public List<Gasto> getAllGastos() {
        return gastoRepository.findAll();
    }

    public Optional<Gasto> getGastoById(Long id) {
        return gastoRepository.findById(id);
    }

    public Gasto createGasto(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    public Gasto updateGasto(Long id, Gasto nuevoGasto){
        return gastoRepository.findById(id).map(gastoExistente -> {
            gastoExistente.setDescri(nuevoGasto.getDescri());
            gastoExistente.setMonto(nuevoGasto.getMonto());
            gastoExistente.setFecha(nuevoGasto.getFecha());
            // Actualiza otros campos según tu modelo

            return gastoRepository.save(gastoExistente);
        }).orElseThrow(() -> new RuntimeException("Gasto no encontrado con id " + id));
    }

    public void deleteGasto(Long id) {
        gastoRepository.deleteById(id);
    }
}
