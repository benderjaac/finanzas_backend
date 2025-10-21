package com.primeng.primeng.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_historico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceHistorico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @Column(name = "monto_disponible", nullable = false)
    private Float montoDisponible = 0f;

    @Column(name = "monto_ahorrado", nullable = false)
    private Float montoAhorrado = 0f;

    @Column(name = "monto_total", nullable = false)
    private Float montoTotal = 0f;

    @Column(nullable = false)
    private LocalDate fecha;
}
