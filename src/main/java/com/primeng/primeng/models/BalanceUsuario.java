package com.primeng.primeng.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "balance_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private User usuario;

    @Column(name = "monto_disponible", nullable = false)
    private Float montoDisponible = 0f;

    @Column(name = "monto_ahorrado", nullable = false)
    private Float montoAhorrado = 0f;

    @Column(name = "ultima_actualizacion", nullable = false)
    private LocalDateTime ultimaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.ultimaActualizacion = LocalDateTime.now();
    }

    // Método helper para obtener el balance total
    public Float getBalanceTotal() {
        return (montoDisponible != null ? montoDisponible : 0f) +
                (montoAhorrado != null ? montoAhorrado : 0f);
    }
}
