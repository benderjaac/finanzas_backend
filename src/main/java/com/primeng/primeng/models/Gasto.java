package com.primeng.primeng.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "gastos")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public Boolean getContado() {
        return contado;
    }

    public void setContado(Boolean contado) {
        this.contado = contado;
    }

}
