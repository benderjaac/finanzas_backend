package com.primeng.primeng.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name ="ahorro_depositos")
@Getter
@Setter
public class AhorroDeposito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ahorro_id")
    private Ahorro ahorro;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Float monto;

    @Column(nullable = false)
    private String descri;

    @Column(nullable = false)
    private String tipo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimiento_id")
    private Movimiento movimiento;
}
