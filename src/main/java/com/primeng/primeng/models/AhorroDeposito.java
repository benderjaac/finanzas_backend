package com.primeng.primeng.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

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
    private Date fecha;

    @Column(nullable = false)
    private Float monto;

    @Column(nullable = false)
    private String descri;
}
