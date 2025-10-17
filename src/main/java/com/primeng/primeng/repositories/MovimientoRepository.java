package com.primeng.primeng.repositories;

import com.primeng.primeng.dto.MovimientoResumenMensualDto;
import com.primeng.primeng.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    Optional<Movimiento> findByIdAndUsuarioId(Long id, Long usuarioId);

    int deleteByIdAndUsuarioId(Long id, Long usuarioId);

    @Query(value = """
    SELECT
        CAST(DATE_TRUNC('month', fecha) AS DATE) AS mes,
        SUM(CASE WHEN tipo = 'Ingreso' THEN monto ELSE 0 END) AS total_ingresos,
        SUM(CASE WHEN tipo = 'Gasto' THEN monto ELSE 0 END) AS total_gastos
    FROM movimientos
    WHERE 
        usuario_id = :usuarioId
        AND fecha >= DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '5 months'
    GROUP BY mes
    ORDER BY mes ASC
    """, nativeQuery = true)
    List<Object[]> obtenerTotalesPorMesYUsuario(@Param("usuarioId") Long usuarioId);
}
