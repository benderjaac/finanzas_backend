package com.primeng.primeng.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "ahorros")
@Getter
@Setter
public class Ahorro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date fecha_inicio;

    @Column(nullable = false)
    private String descri;

    @Column
    private Float monto_meta;

    @Column
    private Float monto_actual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User usuario;

}
