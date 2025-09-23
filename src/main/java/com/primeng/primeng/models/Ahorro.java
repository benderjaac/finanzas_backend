package com.primeng.primeng.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ahorros")
@Getter
@Setter
public class Ahorro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha_inicio;

    @Column(nullable = false)
    private String descri;

    @Column(nullable = false)
    private Float monto_meta;

    @Column(nullable = false)
    private Float monto_actual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User usuario;

}
