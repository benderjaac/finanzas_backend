package com.primeng.primeng.services;

import com.primeng.primeng.dto.AhorroCreateDto;
import com.primeng.primeng.dto.AhorroDepositoCreateDto;
import com.primeng.primeng.dto.AhorroDepositoDto;
import com.primeng.primeng.dto.AhorroDepositoDto;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.Ahorro;
import com.primeng.primeng.models.AhorroDeposito;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Catalogo;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.AhorroDepositoRepository;
import com.primeng.primeng.repositories.AhorroRepository;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.util.Type;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AhorroDepositoService {
    @Autowired
    private AhorroRepository ahorroRepository;

    @Autowired
    private AhorroDepositoRepository ahorroDepositoRepository;

    @Autowired
    private DBRepository db;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public Result<AhorroDepositoDto> findAll(Query query, Long ahorroId){

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Ahorro ahorro = ahorroRepository.findByIdAndUsuarioId(ahorroId, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.AHORRO, ahorroId));

        query.addFilter("ahorro.id", "=", ahorro.getId().toString(), "number");

        Result<AhorroDeposito> result = db.findAll(AhorroDeposito.class, query, false);
        List<AhorroDepositoDto> resultList = result.getData().stream()
                .map(AhorroDepositoDto::new)
                .collect(Collectors.toList());
        return new Result<>(resultList, result.getPagination());
    }

    @Transactional
    public AhorroDepositoDto createAhorroDeposito(AhorroDepositoCreateDto ahorroDepositoCdto, Long ahorroId) {

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Ahorro ahorro = ahorroRepository.findByIdAndUsuarioId(ahorroId, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.AHORRO, ahorroId));

        AhorroDeposito ahorroDeposito = new AhorroDeposito();

        ahorroDeposito.setDescri(ahorroDepositoCdto.getDescri());
        ahorroDeposito.setFecha(ahorroDepositoCdto.getFecha());
        ahorroDeposito.setMonto(ahorroDepositoCdto.getMonto());
        ahorroDeposito.setAhorro(ahorro);

        ahorroDepositoRepository.save(ahorroDeposito);

        return new AhorroDepositoDto(ahorroDeposito);
    }

    @Transactional
    public AhorroDepositoDto updateAhorroDeposito(Long ahorroDepositoId, AhorroDepositoCreateDto ahorroDepositoCdto, Long ahorroId) {

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Ahorro ahorro = ahorroRepository.findByIdAndUsuarioId(ahorroId, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.AHORRO, ahorroId));

        AhorroDeposito ahorroDeposito = ahorroDepositoRepository.findById(ahorroDepositoId).orElseThrow(() -> new NotFoundException(Type.AHORRODEPOSITO, ahorroDepositoId));

        ahorroDeposito.setDescri(ahorroDepositoCdto.getDescri());
        ahorroDeposito.setFecha(ahorroDepositoCdto.getFecha());
        ahorroDeposito.setMonto(ahorroDepositoCdto.getMonto());

        ahorroDepositoRepository.save(ahorroDeposito);

        return new AhorroDepositoDto(ahorroDeposito);
    }

    @Transactional
    public void deleteAhorroDeposito(Long ahorroDepositoId, Long ahorroId) {

        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        Ahorro ahorro = ahorroRepository.findByIdAndUsuarioId(ahorroId, usuario.getId()).orElseThrow(() -> new NotFoundException(Type.AHORRO, ahorroId));

        int deleted = ahorroDepositoRepository.deleteByIdAndAhorroId(ahorroDepositoId, ahorro.getId());
        if (deleted == 0) {
            throw new NotFoundException(Type.AHORRODEPOSITO, ahorroDepositoId);
        }
    }
}
