package com.primeng.primeng.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "gastos")
@Getter
@Setter
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private String descri;

    @Column(nullable = false)
    private Float monto;

    @Column(nullable = false)
    private Long idusuario;

    @Column(nullable = false)
    private Boolean contado;

    public Gasto(){}

    public Gasto(Date fecha, String descri, Float monto, Long idusuario) {
        this.fecha = fecha;
        this.descri = descri;
        this.monto = monto;
        this.idusuario = idusuario;
        this.contado=false;
    }
}
